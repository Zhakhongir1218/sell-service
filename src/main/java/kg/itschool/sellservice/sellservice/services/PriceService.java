package kg.itschool.sellservice.sellservice.services;

import kg.itschool.sellservice.sellservice.models.dtos.PriceDTOS.PriceDTO;
import org.springframework.http.ResponseEntity;

public interface PriceService {
    ResponseEntity<?> createNewPrice(PriceDTO priceDTO);
}
