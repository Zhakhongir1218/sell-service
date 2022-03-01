package kg.itschool.sellservice.sellservice.repositories;

import kg.itschool.sellservice.sellservice.models.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepo extends JpaRepository<Price, Long> {
}
