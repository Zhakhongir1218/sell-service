package kg.itschool.sellservice.sellservice.services.impl;


import io.jsonwebtoken.*;
import kg.itschool.sellservice.sellservice.exceptions.UserNotFoundException;
import kg.itschool.sellservice.sellservice.mappers.CodeMapper;
import kg.itschool.sellservice.sellservice.mappers.UserMapper;
import kg.itschool.sellservice.sellservice.models.dtos.CodesDTOS.CodeDTO;
import kg.itschool.sellservice.sellservice.models.dtos.UserDTOS.UserDTO;
import kg.itschool.sellservice.sellservice.models.entities.Code;
import kg.itschool.sellservice.sellservice.models.entities.User;
import kg.itschool.sellservice.sellservice.models.enums.CodeStatus;
import kg.itschool.sellservice.sellservice.repositories.CodeRepo;
import kg.itschool.sellservice.sellservice.services.CodeService;
import kg.itschool.sellservice.sellservice.services.RequestService;
import kg.itschool.sellservice.sellservice.services.SendSimpleMessage;
import kg.itschool.sellservice.sellservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Pattern;

import static org.springframework.security.crypto.bcrypt.BCrypt.gensalt;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private CodeRepo codeRepo;
    @Autowired
    private SendSimpleMessage sendSimpleMessage;

    @Autowired
    private UserService userService;

    @Value("${jwtSecret}")
    private String secretKey;
    @Autowired
    private RequestService requestService;


    //Метод генерирует и отправляет письмо пользователю по его логину
    //одновременно сохраняя зашифрованные данные в БД.
    @Override
    public ResponseEntity<?> generateCodeAndSendIt(UserDTO userDTO) {
        UserDTO userDTO1 = userService.findUserByLogin(userDTO.getLogin());
        User user = UserMapper.INSTANCE.toUser(userDTO1);
        Code codeToDelete = codeRepo.findByUserAndCodeStatus(user,CodeStatus.NEW);
        if(Objects.nonNull(codeToDelete)) {
            codeToDelete.setCode_status(CodeStatus.CANCELLED);
            codeRepo.save(codeToDelete);
        }

            CodeDTO codeDTO = new CodeDTO();
            int codeRandomizer = 1000 + (int) (((Math.random() * 9999)));
            String hashedCode = BCrypt.hashpw(Integer.toString(codeRandomizer), gensalt());
            codeDTO.setStart_date(LocalDateTime.now());
            codeDTO.setEnd_date(codeDTO.getStart_date().plusMinutes(4));
            codeDTO.setCode_status(CodeStatus.NEW);
            codeDTO.setCode(hashedCode);
            codeDTO.setUser(userDTO1);
            Code code = CodeMapper.INSTANCE.toCode(codeDTO);
            codeRepo.save(code);
            sendSimpleMessage.sendSimpleMessage(userDTO.getLogin(), Integer.toString(codeRandomizer));

        return ResponseEntity.ok("Код успешно отправлен");

    }

    @Override
    public ResponseEntity<?> codeConfirmation(String code, String login) {
        UserDTO userResponseDTO= userService.findUserByLogin(login);
        String loginValidate="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if (!Pattern.matches(loginValidate,login)){
            return new ResponseEntity<>("вы ввели некоректный email",HttpStatus.CONFLICT);
        }

        if (Objects.isNull(userResponseDTO)){
            return new ResponseEntity<>("такого пользователя нет ", HttpStatus.CONFLICT);
        }

        boolean verification= userBlockDateChecking(userResponseDTO);
        if (verification){
            return new ResponseEntity<>("Превышено количество попыток. Повторите в "+ userResponseDTO.getBlock_date(),HttpStatus.CONFLICT);
        }

        Code codeChecking =codeRepo.findByUserAndCodeStatus(UserMapper.INSTANCE.toUser(userResponseDTO),CodeStatus.NEW);
        if (LocalDateTime.now().isAfter(codeChecking.getEnd_date())){
            return new ResponseEntity<>("Время действия кода истек! Получите код повторно",HttpStatus.CONFLICT);
        }

        if (!BCrypt.checkpw(code, codeChecking.getCode())){
            requestService.saveRequest(codeChecking, false);
            if (requestService.countOfFailed(codeChecking) >= 3){
                userResponseDTO.setBlock_date(LocalDateTime.now().plusMinutes(3));
                userService.saveUser(UserMapper.INSTANCE.toUser(userResponseDTO));

                codeChecking.setCode_status(CodeStatus.FAILED);
                codeRepo.saveAndFlush(codeChecking);
            }
            return new ResponseEntity<>("Код не совподает",HttpStatus.ACCEPTED);
        }

        requestService.saveRequest(codeChecking, true);

        Calendar tokensTimeLive =
                Calendar.getInstance();
        tokensTimeLive
                .add(Calendar.MINUTE, 4);

        String token = Jwts.builder()
                .claim("login", login)
                .setExpiration(tokensTimeLive.getTime())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        codeChecking.setCode_status(CodeStatus.APPROVED);
        codeRepo.saveAndFlush(codeChecking);
        return new ResponseEntity<>("Ваш код успешно подтверждён: "+ token,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> verifyToken(String token) {
        try {
            Jws<Claims> jwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return ResponseEntity.ok(jwt.getBody().get("login"));
        } catch (ExpiredJwtException jwtException) {

            return new ResponseEntity<>("Время действия токена истек", HttpStatus.CONFLICT);
        } catch (UnsupportedJwtException jwtException) {

            return new ResponseEntity<>("Неподерживаемый токен", HttpStatus.CONFLICT);
        } catch (MalformedJwtException jwtException) {

            return new ResponseEntity<>("Некорректный токен", HttpStatus.CONFLICT);
        } catch (SignatureException signatureException) {

            return new ResponseEntity<>("Некорректная подпись в токене!", HttpStatus.CONFLICT);
        } catch (Exception exception) {

            return new ResponseEntity<>("unauthorized", HttpStatus.CONFLICT);
        }
    }

    public boolean userBlockDateChecking(UserDTO userResponseDTO) {
        if (Objects.nonNull(userResponseDTO.getBlock_date())){
            return LocalDateTime.now().isBefore(userResponseDTO.getBlock_date());
        }
        return false;
    }
}