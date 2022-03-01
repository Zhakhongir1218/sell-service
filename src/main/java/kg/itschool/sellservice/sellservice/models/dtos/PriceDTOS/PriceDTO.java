package kg.itschool.sellservice.sellservice.models.dtos.PriceDTOS;


import kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS.ProductDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceDTO  {

    Long id;

    Double price;

    LocalDateTime start_date;

    LocalDateTime end_date;

    ProductDTO product;


}
