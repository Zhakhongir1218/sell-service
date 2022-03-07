package kg.itschool.sellservice.sellservice.repositories;

import kg.itschool.sellservice.sellservice.models.entities.OperationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationDetailsRepo extends JpaRepository<OperationDetails, Long> {

}
