package kg.itschool.sellservice.sellservice.services;

import kg.itschool.sellservice.sellservice.models.dtos.operationsandpayments.ForInputDataFromUserToOperations;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OperationService {
    ResponseEntity<?> provideOperation(String token, List<ForInputDataFromUserToOperations> sellingList);

    ResponseEntity<?> payment(String token, Long operationId, double cash);
}
