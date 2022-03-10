package kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinalizationProductDTO {
    ProductDTO productDTO;
    Integer quantity;
    Double price;
}
