package kg.itschool.sellservice.sellservice.services.impl;

import kg.itschool.sellservice.sellservice.exceptions.UserALreadyExistException;
import kg.itschool.sellservice.sellservice.mappers.UserMapper;
import kg.itschool.sellservice.sellservice.models.dtos.UserDTOS.UserDTO;
import kg.itschool.sellservice.sellservice.models.entities.User;
import kg.itschool.sellservice.sellservice.repositories.UserRepo;
import kg.itschool.sellservice.sellservice.services.CodeService;
import kg.itschool.sellservice.sellservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public ResponseEntity<?> createNewUser(UserDTO userDTO) {

        User user = UserMapper.INSTANCE.toUser(userDTO);
        User containing = userRepo.findByLogin(user.getLogin());
        if(Objects.isNull(containing)){
           userRepo.save(user);
        }else{
            return new ResponseEntity<>(new UserALreadyExistException(user.getLogin() + " Уже существует!" ), HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok("Новый пользователь " + userDTO.getLogin() + " успешно сохранен.");
    }

    @Override
    public UserDTO findUserByLogin(String login){
        User user =  userRepo.findByLogin(login);
        if(Objects.isNull(user)){
            throw new UserALreadyExistException("Данный пользователь не сущетсвует");
        }
        return UserMapper.INSTANCE.toUserDTO(user);
    }



}
