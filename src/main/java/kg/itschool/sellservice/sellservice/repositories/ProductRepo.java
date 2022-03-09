package kg.itschool.sellservice.sellservice.repositories;

import kg.itschool.sellservice.sellservice.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByName(String name);
    Product findProductById(Long id);
}
