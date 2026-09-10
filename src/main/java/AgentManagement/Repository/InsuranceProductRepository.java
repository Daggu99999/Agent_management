package AgentManagement.Repository;

import AgentManagement.Entity.InsuranceProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceProductRepository
        extends JpaRepository<InsuranceProduct, Long> {
}