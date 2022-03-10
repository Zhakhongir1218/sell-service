package kg.itschool.sellservice.sellservice.services;

import kg.itschool.sellservice.sellservice.models.dtos.CodesDTOS.CodeDTO;
import kg.itschool.sellservice.sellservice.models.dtos.UserDTOS.UserDTO;
import org.springframework.http.ResponseEntity;

public interface CodeService {
    ResponseEntity<?> generateCodeAndSendIt(UserDTO userDTO);
    ResponseEntity<?> codeConfirmation(String codef, String login);
    ResponseEntity<?> verifyToken(String token);
    UserDTO findByLogin(String login);

}
