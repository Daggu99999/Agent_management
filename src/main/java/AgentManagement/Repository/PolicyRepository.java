package AgentManagement.Repository;

import AgentManagement.Entity.Policy;
import AgentManagement.Entity.PolicyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyRepository
        extends JpaRepository<Policy, Long> {

    List<Policy> findByCustomer_Id(Long customerId);
    long countByStatus(PolicyStatus status);

}