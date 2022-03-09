package kg.itschool.sellservice.sellservice.controllers;

import kg.itschool.sellservice.sellservice.models.dtos.CategoriesDTOS.CategoryDTO;
import kg.itschool.sellservice.sellservice.models.dtos.PriceDTOS.PriceDTO;
import kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS.ProductDTO;
import kg.itschool.sellservice.sellservice.models.entities.Product;
import kg.itschool.sellservice.sellservice.services.CategoryService;
import kg.itschool.sellservice.sellservice.services.PriceService;
import kg.itschool.sellservice.sellservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private  CategoryService categoryService;
    @Autowired
    private PriceService priceService;



    @PostMapping("/add")
    public ResponseEntity<?> createNewProduct(@RequestHeader String token, @RequestBody ProductDTO productDTO) {

        return productService.createNewProduct(token, productDTO);
    }

    @PostMapping("/category")
    public ResponseEntity<?> createNewCategory(@RequestHeader String token, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(token,categoryDTO);
    }

    @GetMapping("/getall")
    public List<Product> getAllProducts() {
        return productService.getAllCategories();
    }




}
