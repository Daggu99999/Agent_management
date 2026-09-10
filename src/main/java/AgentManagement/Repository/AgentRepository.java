package AgentManagement.Repository;

import AgentManagement.DTO.UpdateProfileRequest;
import AgentManagement.Entity.Agent;
import AgentManagement.Entity.Role;
import AgentManagement.Entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Agent> findByEmail(String email);

    long countByStatus(Status status);



    List<Agent> findByRole(Role role);

    // Add these methods for counting customers
    long countByRole(Role role);

    long countByRoleAndStatus(Role role, Status status);
   

}