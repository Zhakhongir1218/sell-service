package kg.itschool.sellservice.sellservice.services;

import kg.itschool.sellservice.sellservice.models.dtos.UserDTOS.UserDTO;
import kg.itschool.sellservice.sellservice.models.entities.User;
import org.mapstruct.control.MappingControl;
import org.springframework.http.ResponseEntity;

public interface UserService {


    ResponseEntity<?> createNewUser(UserDTO userDTO);

    UserDTO findUserByLogin(String login);

    void saveUser(User user);


}
