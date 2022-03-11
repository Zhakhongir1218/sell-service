package kg.itschool.sellservice.sellservice.controllers;

import kg.itschool.sellservice.sellservice.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    @Autowired
    private OperationService operationService;


}
