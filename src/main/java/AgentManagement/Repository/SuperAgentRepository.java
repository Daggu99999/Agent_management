package AgentManagement.Repository;

import AgentManagement.Entity.SuperAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperAgentRepository extends JpaRepository<SuperAgent, Long> {

    Optional<SuperAgent> findByEmail(String email);
   

}