package AgentManagement.ServiceImpl;

import AgentManagement.DTO.AgentDashboardDTO;
import AgentManagement.DTO.ResetPasswordRequest;
import AgentManagement.DTO.UpdateProfileRequest;
import AgentManagement.Entity.Agent;
import AgentManagement.Entity.Status;
import AgentManagement.Entity.Role;
import AgentManagement.Entity.SuperAgent;
import AgentManagement.Repository.AgentRepository;
import AgentManagement.Repository.SuperAgentRepository;
import AgentManagement.Service.AgentService;
import AgentManagement.Service.PolicyService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
class AgentServiceImpl implements AgentService {

    private final PasswordEncoder passwordEncoder;
    private final AgentRepository agentRepository;
    private final SuperAgentRepository superAgentRepository;



    public AgentServiceImpl(
            AgentRepository agentRepository,
            SuperAgentRepository superAgentRepository,
            PasswordEncoder passwordEncoder) {

        this.agentRepository = agentRepository;
        this.superAgentRepository = superAgentRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public Agent registerAgent(Agent agent, Long superAgentId) {

        if (agent.getRole() == Role.ROLE_AGENT) {

            agent.setStatus(Status.PENDING);

            SuperAgent superAgent = superAgentRepository.findById(superAgentId)
                    .orElseThrow(() ->
                            new RuntimeException("Super Agent not found"));

            agent.setSuperAgent(superAgent);

        } else if (agent.getRole() == Role.ROLE_CUSTOMER) {

            agent.setStatus(Status.ACTIVE);
            agent.setSuperAgent(null);
        }

        agent.setPassword(
                passwordEncoder.encode(agent.getPassword()));

        return agentRepository.save(agent);
    }

    @Override
    public List<Agent> getAllAgents() {

        return agentRepository.findAll();
    }

    @Override
    public Agent getAgentById(Long id) {

        return agentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Agent not found"));
    }

    @Override
    public AgentDashboardDTO getAgentDashboard(Long agentId) {

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() ->
                        new RuntimeException("Agent not found"));



        return AgentDashboardDTO.builder()
                .agentId(agent.getId())
                .agentName(agent.getFirstName())


                .build();
    }

    @Override
    public String resetPassword(Long agentId,
                                ResetPasswordRequest request) {

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() ->
                        new RuntimeException("Agent not found"));

        agent.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()));

        agentRepository.save(agent);



        return "Password reset successfully";
    }

    @Override
    public Agent getProfile(Long userId) {

        return agentRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @Override
    public Agent updateProfile(
            Long userId,
            UpdateProfileRequest request) {

        Agent agent = agentRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        agent.setFirstName(request.getFirstName());
        agent.setLastName(request.getLastName());
        agent.setMobileNumber(request.getMobileNumber());

        return agentRepository.save(agent);
    }

    //superagentcustomer related activate and deactive
    @Override
    public List<Agent> getAllCustomers() {
        return agentRepository.findByRole(Role.ROLE_CUSTOMER);
    }

    @Override
    public void activateCustomer(Long customerId) {

        Agent customer = agentRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        customer.setStatus(Status.ACTIVE);

        agentRepository.save(customer);
    }

    @Override
    public void deactivateCustomer(Long customerId) {

        Agent customer = agentRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        customer.setStatus(Status.INACTIVE);

        agentRepository.save(customer);
    }

    @Override
    public void changeRole(Long customerId) {

        Agent user = agentRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (user.getRole() == Role.ROLE_CUSTOMER) {

            user.setRole(Role.ROLE_AGENT);

        } else {

            user.setRole(Role.ROLE_CUSTOMER);
        }

        agentRepository.save(user);
    }

    //agent approve reject

    @Override
    public void approveAgent(Long id) {

        Agent agent = agentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Agent not found"));

        agent.setStatus(Status.APPROVED);

        agentRepository.save(agent);
    }
    @Override
    public void rejectAgent(Long id) {

        Agent agent = agentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Agent not found"));

        agent.setStatus(Status.REJECTED);

        agentRepository.save(agent);
    }
    //create agent

    @Override
    public void createAgent(Agent agent) {

        agent.setRole(Role.ROLE_AGENT);

        agent.setStatus(Status.PENDING);

        agentRepository.save(agent);
    }


}