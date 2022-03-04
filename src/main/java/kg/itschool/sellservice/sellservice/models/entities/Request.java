package kg.itschool.sellservice.sellservice.models.entities;

import com.fasterxml.jackson.databind.ser.Serializers;
import kg.itschool.sellservice.sellservice.models.entities.base_entity.Base_Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "requests")
public class Request extends Base_Entity {
    Boolean success;
    LocalDateTime add_date;
    @OneToOne
    @JoinColumn(name = "id_codes")
    Code code;

}
