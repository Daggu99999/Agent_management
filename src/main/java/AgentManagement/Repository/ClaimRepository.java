package AgentManagement.Repository;

import AgentManagement.Entity.Agent;
import AgentManagement.Entity.Claim;
import AgentManagement.Entity.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

    long countByStatus(ClaimStatus status);


}
