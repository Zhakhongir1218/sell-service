package kg.itschool.sellservice.sellservice.services.impl;

import kg.itschool.sellservice.sellservice.exceptions.CategoryAlreadyExistsException;
import kg.itschool.sellservice.sellservice.mappers.CategoryMapper;
import kg.itschool.sellservice.sellservice.models.dtos.CategoriesDTOS.CategoryDTO;
import kg.itschool.sellservice.sellservice.models.entities.Category;
import kg.itschool.sellservice.sellservice.repositories.CategoryRepo;
import kg.itschool.sellservice.sellservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;


    @Override
    public ResponseEntity<?> createCategory(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.INSTANCE.toCategory(categoryDTO);
        Category containing = categoryRepo.findByName(category);
        if(Objects.isNull(containing)){
            categoryRepo.save(category);
        }else{
            return new ResponseEntity<>(new CategoryAlreadyExistsException("Данная категория уже существует"), HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok("Новая категория " + category.getName()+ " успешно сохранена");
    }
}
