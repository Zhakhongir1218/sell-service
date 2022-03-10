package kg.itschool.sellservice.sellservice.controllers;

import kg.itschool.sellservice.sellservice.models.dtos.operationsandpayments.ForInputDataFromUserToOperations;
import kg.itschool.sellservice.sellservice.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/operation")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @PostMapping("/provideOperation")
    public ResponseEntity<?> provideOperation(@RequestHeader String token, @RequestBody List<ForInputDataFromUserToOperations> sellingList ) {
        return operationService.provideOperation(token, sellingList);
    }
}
