package kg.itschool.sellservice.sellservice.models.dtos.OperationDetailsDTOS;

import kg.itschool.sellservice.sellservice.models.dtos.OperationsDTOS.OperationDTO;
import kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS.ProductDTO;
import kg.itschool.sellservice.sellservice.models.entities.Operation;
import kg.itschool.sellservice.sellservice.models.entities.Product;
import kg.itschool.sellservice.sellservice.models.entities.base_entity.Base_Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OperationDetailsDTO extends Base_Entity {

    ProductDTO productDTO;

    OperationDTO operationDTO;

    Integer amount;

    Integer quantity;
}
