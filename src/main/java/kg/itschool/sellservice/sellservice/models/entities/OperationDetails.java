package kg.itschool.sellservice.sellservice.models.entities;

import kg.itschool.sellservice.sellservice.models.entities.base_entity.Base_Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "operation_details")
public class OperationDetails extends Base_Entity {

    @ManyToOne
    @JoinColumn(name = "id_products")
    Product product;

    @ManyToOne
    @JoinColumn(name = "id_operations")
    Operation operation;

    Integer amount;

    Integer quantity;
}
