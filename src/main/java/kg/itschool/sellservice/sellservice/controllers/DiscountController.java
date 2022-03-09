package kg.itschool.sellservice.sellservice.controllers;

import kg.itschool.sellservice.sellservice.models.dtos.DiscountDTOS.DiscountDTO;
import kg.itschool.sellservice.sellservice.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/discount")
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewDiscount(@RequestHeader String token, @RequestBody DiscountDTO discountDTO){
        return discountService.createNewDiscount(token, discountDTO);
    }


}
