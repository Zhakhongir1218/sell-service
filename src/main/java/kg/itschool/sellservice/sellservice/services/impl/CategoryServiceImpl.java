package kg.itschool.sellservice.sellservice.services.impl;

import kg.itschool.sellservice.sellservice.exceptions.CategoryAlreadyExistsException;
import kg.itschool.sellservice.sellservice.mappers.CategoryMapper;
import kg.itschool.sellservice.sellservice.models.dtos.CategoriesDTOS.CategoryDTO;
import kg.itschool.sellservice.sellservice.models.entities.Category;
import kg.itschool.sellservice.sellservice.repositories.CategoryRepo;
import kg.itschool.sellservice.sellservice.services.CategoryService;
import kg.itschool.sellservice.sellservice.services.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CodeService codeService;


    @Override
    public ResponseEntity<?> createCategory(String token, CategoryDTO categoryDTO) {
        ResponseEntity<?> responseEntity = codeService.verifyToken(token);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)){
            return responseEntity;
        }
        Category category = CategoryMapper.INSTANCE.toCategory(categoryDTO);
        Category containing = categoryRepo.findByName(category.getName());
        if(Objects.isNull(containing)){
            categoryRepo.save(category);
        }else{
            return new ResponseEntity<>(new CategoryAlreadyExistsException("Данная категория уже существует"), HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok("Новая категория " + category.getName()+ " успешно сохранена");
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepo.findDistinctById(id);

    }
}
