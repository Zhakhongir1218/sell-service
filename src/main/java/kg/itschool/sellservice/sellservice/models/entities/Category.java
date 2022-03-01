package kg.itschool.sellservice.sellservice.models.entities;

import kg.itschool.sellservice.sellservice.models.entities.base_entity.Base_Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "categories")
public class Category extends Base_Entity {

    String name;

    boolean active;


}
