package kg.itschool.sellservice.sellservice.services;

import kg.itschool.sellservice.sellservice.models.dtos.CategoriesDTOS.CategoryDTO;
import kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS.ProductDTO;
import kg.itschool.sellservice.sellservice.models.entities.Category;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<?> createCategory(String token,CategoryDTO categoryDTO);

    Category findCategoryById(Long id);
}
