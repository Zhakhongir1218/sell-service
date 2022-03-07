package kg.itschool.sellservice.sellservice.services;

import kg.itschool.sellservice.sellservice.models.dtos.DiscountDTOS.DiscountDTO;
import org.springframework.http.ResponseEntity;

public interface DiscountService {
    ResponseEntity<?> createNewDiscount(DiscountDTO discountDTO);
}
