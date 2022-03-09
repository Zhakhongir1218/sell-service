package kg.itschool.sellservice.sellservice.repositories;

import kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS.ProductDTO;
import kg.itschool.sellservice.sellservice.models.entities.Price;
import kg.itschool.sellservice.sellservice.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepo extends JpaRepository<Price, Long> {
    List<Price> findAllByProduct(Product product);
    Price findPriceById(Long id);
}
