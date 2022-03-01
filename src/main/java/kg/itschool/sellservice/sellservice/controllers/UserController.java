package kg.itschool.sellservice.sellservice.controllers;

import kg.itschool.sellservice.sellservice.models.dtos.UserDTOS.UserDTO;
import kg.itschool.sellservice.sellservice.services.CodeService;
import kg.itschool.sellservice.sellservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CodeService codeService;


    @PostMapping("/create")
    public ResponseEntity<?> createNewUser(@RequestBody UserDTO userDTO) {
        return userService.createNewUser(userDTO);
    }


    @PostMapping("/send")
    public ResponseEntity<?> sendEmailToUser(@RequestBody UserDTO userDTO){
        return codeService.generateCodeAndSendIt(userDTO);
    }


    @PostMapping("/confirm")
    public ResponseEntity<?> codeConfirmation(@RequestParam Integer code, String login){

        return codeService.codeConfirmation(code,login);
    }


}
