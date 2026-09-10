package AgentManagement.Repository;

import AgentManagement.Entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimsRepository extends JpaRepository<Claim,Long> {

}
