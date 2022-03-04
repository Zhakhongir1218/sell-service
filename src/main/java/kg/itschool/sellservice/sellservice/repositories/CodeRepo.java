package kg.itschool.sellservice.sellservice.repositories;

import kg.itschool.sellservice.sellservice.models.dtos.CodesDTOS.CodeDTO;
import kg.itschool.sellservice.sellservice.models.dtos.UserDTOS.UserDTO;
import kg.itschool.sellservice.sellservice.models.entities.Category;
import kg.itschool.sellservice.sellservice.models.entities.Code;
import kg.itschool.sellservice.sellservice.models.entities.User;
import kg.itschool.sellservice.sellservice.models.enums.CodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepo extends JpaRepository<Code, Long> {
    @Query(value = "select * from codes where code=?1",nativeQuery = true)
    Code findByCodeEquals(String code);
    @Query(value = "select * from codes where id_user=?1 ORDER BY  start_date desc limit(1)",nativeQuery = true)
    Code findByUser(User user);

    @Query(value = "select * from codes where id_user=?1 and code_status = 'NEW' ORDER BY start_date desc limit(1) ",nativeQuery = true)
    Code findByUserAndCodeStatus(User user, CodeStatus codeStatus);
}