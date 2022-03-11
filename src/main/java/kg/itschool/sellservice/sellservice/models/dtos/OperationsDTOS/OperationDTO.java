package kg.itschool.sellservice.sellservice.models.dtos.OperationsDTOS;

import kg.itschool.sellservice.sellservice.models.dtos.UserDTOS.UserDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OperationDTO {

    Long id;

    LocalDateTime add_date;

    Double total_price;

    Double change;


    UserDTO userDTO;

    Double cash;

}
