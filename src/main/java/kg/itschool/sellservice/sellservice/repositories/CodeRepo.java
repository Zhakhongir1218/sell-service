package kg.itschool.sellservice.sellservice.repositories;

import kg.itschool.sellservice.sellservice.models.dtos.CodesDTOS.CodeDTO;
import kg.itschool.sellservice.sellservice.models.dtos.UserDTOS.UserDTO;
import kg.itschool.sellservice.sellservice.models.entities.Category;
import kg.itschool.sellservice.sellservice.models.entities.Code;
import kg.itschool.sellservice.sellservice.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepo extends JpaRepository<Code, Long> {
    @Query(value = "select * from codes where code=?1",nativeQuery = true)
    Code findByCodeEquals(String code);

    Code findByUser(UserDTO userDTO);

    @Query(value = "select * from codes where user_id=?1 order by start_date desc limit (1)",nativeQuery = true)
    Code findByUserAndCodeStatus(User user);
}