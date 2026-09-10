package AgentManagement.Service;

import AgentManagement.DTO.AgentDashboardDTO;
import AgentManagement.DTO.ResetPasswordRequest;
import AgentManagement.DTO.UpdateProfileRequest;
import AgentManagement.Entity.Agent;

import java.util.List;

public interface AgentService {

    Agent registerAgent(Agent agent, Long superAgentId);

    List<Agent> getAllAgents();

    Agent getAgentById(Long id);
    AgentDashboardDTO getAgentDashboard(Long agentId);
    String resetPassword(Long agentId,
                         ResetPasswordRequest request);Agent getProfile(Long userId);

    Agent updateProfile(Long userId,
                        UpdateProfileRequest request);

    List<Agent> getAllCustomers();

    void activateCustomer(Long customerId);

    void deactivateCustomer(Long customerId);

    void changeRole(Long customerId);
    void approveAgent(Long id);

    void rejectAgent(Long id);

    void createAgent(Agent agent);

}