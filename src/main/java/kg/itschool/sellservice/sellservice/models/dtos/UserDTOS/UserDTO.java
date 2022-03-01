package kg.itschool.sellservice.sellservice.models.dtos.UserDTOS;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {


    Long id;

    String name;

    String login;

    boolean active;

    LocalDateTime block_date;




}
