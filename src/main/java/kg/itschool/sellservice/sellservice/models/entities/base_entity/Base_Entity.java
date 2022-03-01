package kg.itschool.sellservice.sellservice.models.entities.base_entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Base_Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
