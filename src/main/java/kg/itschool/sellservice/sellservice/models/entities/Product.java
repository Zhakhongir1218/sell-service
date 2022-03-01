package kg.itschool.sellservice.sellservice.models.entities;

import kg.itschool.sellservice.sellservice.models.entities.base_entity.Base_Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "products")
public class Product extends Base_Entity {
    String name;

    String barcode;

    @OneToOne
    @JoinColumn(name = "id_categories")
    Category category;

    boolean active;
}
