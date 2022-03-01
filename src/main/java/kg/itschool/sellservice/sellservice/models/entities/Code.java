package kg.itschool.sellservice.sellservice.models.entities;

import kg.itschool.sellservice.sellservice.models.entities.base_entity.Base_Entity;
import kg.itschool.sellservice.sellservice.models.enums.CodeStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "codes")
public class Code extends Base_Entity {
    String code;

    LocalDateTime start_date;

    LocalDateTime end_date;

    @Enumerated(value = EnumType.STRING)
    CodeStatus code_status;

    @OneToOne
    @JoinColumn(name = "id_user")
    User user;

}
