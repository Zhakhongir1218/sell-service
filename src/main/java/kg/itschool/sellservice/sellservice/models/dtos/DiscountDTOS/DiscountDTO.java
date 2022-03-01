package kg.itschool.sellservice.sellservice.models.dtos.DiscountDTOS;

import kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS.ProductDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountDTO  {

    Long id;

    Integer discount;

    LocalDateTime start_date;

    LocalDateTime end_date;

    ProductDTO product;



}
