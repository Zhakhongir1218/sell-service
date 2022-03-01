package kg.itschool.sellservice.sellservice.services.impl;


import kg.itschool.sellservice.sellservice.exceptions.UserNotFoundException;
import kg.itschool.sellservice.sellservice.mappers.CodeMapper;
import kg.itschool.sellservice.sellservice.mappers.UserMapper;
import kg.itschool.sellservice.sellservice.models.dtos.CodesDTOS.CodeDTO;
import kg.itschool.sellservice.sellservice.models.dtos.UserDTOS.UserDTO;
import kg.itschool.sellservice.sellservice.models.entities.Code;
import kg.itschool.sellservice.sellservice.models.enums.CodeStatus;
import kg.itschool.sellservice.sellservice.repositories.CodeRepo;
import kg.itschool.sellservice.sellservice.services.CodeService;
import kg.itschool.sellservice.sellservice.services.SendSimpleMessage;
import kg.itschool.sellservice.sellservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.security.crypto.bcrypt.BCrypt.gensalt;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private CodeRepo codeRepo;
    @Autowired
    private SendSimpleMessage sendSimpleMessage;

    @Autowired
    private UserService userService;




    //Метод генерирует и отправляет письмо пользователю по его логину
    //одновременно сохраняя зашифрованные данные в БД.
    @Override
    public ResponseEntity<?> generateCodeAndSendIt(UserDTO userDTO) {

        userDTO = userService.findUserByLogin(userDTO);
        CodeDTO codeDTO = new CodeDTO();
        int codeRandomizer = (int) (((Math.random() * 9999 - 1000) + 1000) + 1);
        String hashedCode = BCrypt.hashpw(Integer.toString(codeRandomizer), gensalt());

        codeDTO.setStart_date(LocalDateTime.now());
        codeDTO.setEnd_date(codeDTO.getStart_date().plusMinutes(4));
        codeDTO.setCode_status(CodeStatus.NEW);
        codeDTO.setCode(hashedCode);
        codeDTO.setId_user(userDTO.getId());
        Code code = CodeMapper.INSTANCE.toCode(codeDTO);
        code.setUser(UserMapper.INSTANCE.toUser(userDTO));
        codeRepo.save(code);
        sendSimpleMessage.sendSimpleMessage(userDTO.getLogin(), Integer.toString(codeRandomizer));

        return ResponseEntity.ok("Код успешно отправлен");
    }

    public ResponseEntity<?> codeConfirmation(Integer codeConf, String login){
        String hashedCode = BCrypt.hashpw(Integer.toString(codeConf), gensalt());
        Code code = codeRepo.findByCodeEquals(hashedCode);
        if(Objects.isNull(code)){
            return new ResponseEntity<>(new UserNotFoundException("Пользователь не найден"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(code.getStart_date().isAfter(code.getEnd_date())){
            code.setCode_status(CodeStatus.CANCELLED);
            codeRepo.save(code);
            throw new UserNotFoundException("Введенный код неверный, попробуйте еще раз");
        }
        code.setCode_status(CodeStatus.APPROVED);
        codeRepo.save(code);


        return ResponseEntity.ok("Вы подтвердили свой вход");
    }
}


