package kg.itschool.sellservice.sellservice.models.dtos.RequestsDTOS;

import kg.itschool.sellservice.sellservice.models.dtos.CodesDTOS.CodeDTO;
import kg.itschool.sellservice.sellservice.models.entities.base_entity.Base_Entity;

import java.time.LocalDateTime;


public class RequestDTO extends Base_Entity {
    Boolean success;
    LocalDateTime add_date;
    CodeDTO code;

}
