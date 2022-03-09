package kg.itschool.sellservice.sellservice.controllers;

import kg.itschool.sellservice.sellservice.models.dtos.PriceDTOS.PriceDTO;
import kg.itschool.sellservice.sellservice.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/price")
public class PriceController {
    @Autowired
    private PriceService priceService;


    @PostMapping("/create")
    public ResponseEntity<?> createNewPrice(@RequestHeader String token, @RequestBody PriceDTO priceDTO){
        return priceService.createNewPrice(token, priceDTO);
    }
}
