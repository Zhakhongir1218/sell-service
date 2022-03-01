package kg.itschool.sellservice.sellservice.repositories;

import kg.itschool.sellservice.sellservice.models.dtos.CodesDTOS.CodeDTO;
import kg.itschool.sellservice.sellservice.models.entities.Category;
import kg.itschool.sellservice.sellservice.models.entities.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepo extends JpaRepository<Code, Long> {
    @Query(value = "select * from codes where code=?1",nativeQuery = true)
    Code findByCodeEquals(String code);
}