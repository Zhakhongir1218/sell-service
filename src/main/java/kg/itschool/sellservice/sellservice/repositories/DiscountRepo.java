package kg.itschool.sellservice.sellservice.repositories;

import kg.itschool.sellservice.sellservice.models.entities.Category;
import kg.itschool.sellservice.sellservice.models.entities.Discount;
import kg.itschool.sellservice.sellservice.models.entities.Price;
import kg.itschool.sellservice.sellservice.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepo extends JpaRepository<Discount, Long> {
    List<Discount> findAllByProduct(Product product);
}
