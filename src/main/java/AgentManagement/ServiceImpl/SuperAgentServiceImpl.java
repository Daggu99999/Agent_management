package AgentManagement.ServiceImpl;

import AgentManagement.DTO.SuperAgentDashboardDTO;
import AgentManagement.DTO.UpdateAgent;
import AgentManagement.Entity.*;
import AgentManagement.Repository.*;
import AgentManagement.Service.SuperAgentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SuperAgentServiceImpl implements SuperAgentService {

    private final SuperAgentRepository superAgentRepository;
    private final AgentRepository agentRepository;

    public SuperAgentServiceImpl(
            SuperAgentRepository superAgentRepository,
            AgentRepository agentRepository


           ) {

        this.superAgentRepository = superAgentRepository;
        this.agentRepository = agentRepository;

    }

    @Override
    public SuperAgent createSuperAgent(SuperAgent superAgent) {

        SuperAgent savedSuperAgent =
                superAgentRepository.save(superAgent);



        return savedSuperAgent;
    }

    @Override
    public List<SuperAgent> getAllSuperAgents() {

        return superAgentRepository.findAll();
    }

    @Override
    public SuperAgent getSuperAgentById(Long id) {

        return superAgentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Super Agent not found with id: " + id));
    }

    @Override
    public Agent approveAgent(Long agentId) {

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() ->
                        new RuntimeException("Agent not found"));

        agent.setStatus(Status.APPROVED);

        Agent savedAgent = agentRepository.save(agent);



        return savedAgent;
    }

    @Override
    public Agent rejectAgent(Long agentId) {

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() ->
                        new RuntimeException("Agent not found"));

        agent.setStatus(Status.REJECTED);

        Agent savedAgent = agentRepository.save(agent);



        return savedAgent;
    }


    @Override
    public SuperAgentDashboardDTO getDashboard() {

        long totalAgents =
                agentRepository.count();

        long approvedAgents =
                agentRepository.countByStatus(
                        Status.APPROVED);

        long pendingAgents =
                agentRepository.countByStatus(
                        Status.PENDING);

        long rejectedAgents =
                agentRepository.countByStatus(
                        Status.REJECTED);



        return SuperAgentDashboardDTO.builder()
                .totalAgents(totalAgents)
                .approvedAgents(approvedAgents)
                .pendingAgents(pendingAgents)
                .rejectedAgents(rejectedAgents)

                .build();
    }
    //updateAgent
    @Override
    public Agent updateAgent(Long agentId, UpdateAgent request) {

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() ->
                        new RuntimeException("Agent not found"));

        agent.setFirstName(request.getName());
        agent.setEmail(request.getEmail());
        agent.setMobileNumber(request.getMobileNumber());
        agent.setStatus(request.getStatus());

        Agent updatedAgent = agentRepository.save(agent);


        return updatedAgent;
    }

    //delete agent
    @Override
    public void deleteAgent(Long agentId) {

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() ->
                        new RuntimeException("Agent not found"));

        agentRepository.delete(agent);
    }


    //cliams methods
    //view allcliams

    //Assign cliam to an agent


}