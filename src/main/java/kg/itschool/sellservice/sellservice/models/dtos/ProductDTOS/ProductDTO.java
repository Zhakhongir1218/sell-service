package kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS;


import kg.itschool.sellservice.sellservice.models.dtos.CategoriesDTOS.CategoryDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {

    Long id;

    String name;

    String barcode;


    CategoryDTO category;

    boolean active;
}
