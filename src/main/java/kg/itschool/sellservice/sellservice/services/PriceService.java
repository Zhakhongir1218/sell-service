package kg.itschool.sellservice.sellservice.services;

import kg.itschool.sellservice.sellservice.models.dtos.PriceDTOS.PriceDTO;
import kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS.ProductDTO;
import org.springframework.http.ResponseEntity;

public interface PriceService {
    ResponseEntity<?> createNewPrice(String token,PriceDTO priceDTO);
    PriceDTO getActualPrice(Long id);
}
