package kg.itschool.sellservice.sellservice.services;

import kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS.ProductDTO;
import kg.itschool.sellservice.sellservice.models.entities.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<?> createNewProduct(String token,ProductDTO productDTO);

    List<Product> getAllCategories();
}
