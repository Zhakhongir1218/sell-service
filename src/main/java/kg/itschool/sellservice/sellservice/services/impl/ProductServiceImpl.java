package kg.itschool.sellservice.sellservice.services.impl;

import kg.itschool.sellservice.sellservice.exceptions.UserNotFoundException;
import kg.itschool.sellservice.sellservice.mappers.CategoryMapper;
import kg.itschool.sellservice.sellservice.mappers.ProductMapper;
import kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS.ProductDTO;
import kg.itschool.sellservice.sellservice.models.entities.Category;
import kg.itschool.sellservice.sellservice.models.entities.Product;
import kg.itschool.sellservice.sellservice.repositories.ProductRepo;
import kg.itschool.sellservice.sellservice.services.CategoryService;
import kg.itschool.sellservice.sellservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private  CategoryService categoryService;


    @Override
    public ResponseEntity<?> createNewProduct(ProductDTO productDTO) {

        Product product = ProductMapper.INSTANCE.toProduct(productDTO);
        Product containing = productRepo.findByName(product.getName());
        if (Objects.nonNull(containing)) {
            return new ResponseEntity<>(new UserNotFoundException("Данный продукт уже сущетсвует"), HttpStatus.CONFLICT);
        }
        product.setBarcode(generateBarcode());
        product.setActive(true);
        product.setCategory(CategoryMapper.INSTANCE.toCategory(productDTO.getCategory()));
        if(Objects.isNull(product.getCategory())){
            return new ResponseEntity<>(new UserNotFoundException("Вы не ввели категорию"), HttpStatus.CONFLICT);
        }
        productRepo.save(product);
        return ResponseEntity.ok("Продукт успешно добавлен в Базу данных");
    }


    private String generateBarcode() {
        String barcodeRandomizerFirstPart = String.valueOf(100000 + (long) (((Math.random() * 999999))));
        String barcodeRandomizerSecondPart = String.valueOf(100000 + (long) (((Math.random() * 999999))));
        return barcodeRandomizerFirstPart.concat(barcodeRandomizerSecondPart);
    }
}
