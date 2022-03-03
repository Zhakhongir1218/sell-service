package kg.itschool.sellservice.sellservice.models.dtos.CodesDTOS;


import kg.itschool.sellservice.sellservice.models.dtos.UserDTOS.UserDTO;
import kg.itschool.sellservice.sellservice.models.enums.CodeStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDateTime;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodeDTO  {

    Long id;

    String code;

    LocalDateTime start_date;

    LocalDateTime end_date;

    CodeStatus code_status;

    UserDTO user;

}
