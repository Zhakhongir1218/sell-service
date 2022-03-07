package kg.itschool.sellservice.sellservice.services.impl;

import kg.itschool.sellservice.sellservice.models.dtos.DiscountDTOS.DiscountDTO;
import kg.itschool.sellservice.sellservice.services.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {


    @Override
    public ResponseEntity<?> createNewDiscount(DiscountDTO discountDTO) {
        return null;
    }
}
