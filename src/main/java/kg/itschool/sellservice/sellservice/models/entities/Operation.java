package kg.itschool.sellservice.sellservice.models.entities;

import kg.itschool.sellservice.sellservice.models.entities.base_entity.Base_Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "operations")
public class Operation extends Base_Entity {

    LocalDateTime add_date;

    Double total_price;

    Double change;

    @ManyToOne
    @JoinColumn(name = "id_user")
    User user;

    Double cash;

}
