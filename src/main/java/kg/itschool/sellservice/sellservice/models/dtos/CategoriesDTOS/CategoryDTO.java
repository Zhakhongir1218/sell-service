package kg.itschool.sellservice.sellservice.models.dtos.CategoriesDTOS;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDTO {
    Long id;

    String name;

    boolean active;


}
