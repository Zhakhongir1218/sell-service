package kg.itschool.sellservice.sellservice.repositories;

import kg.itschool.sellservice.sellservice.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findByLogin(String login);


}
