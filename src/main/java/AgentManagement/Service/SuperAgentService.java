package AgentManagement.Service;

import AgentManagement.DTO.SuperAgentDashboardDTO;
import AgentManagement.DTO.UpdateAgent;
import AgentManagement.Entity.Agent;
import AgentManagement.Entity.Claim;
import AgentManagement.Entity.Payment;
import AgentManagement.Entity.SuperAgent;

import java.util.List;

public interface SuperAgentService {

    SuperAgent createSuperAgent(SuperAgent superAgent);

    List<SuperAgent> getAllSuperAgents();

    SuperAgent getSuperAgentById(Long id);

    Agent approveAgent(Long agentId);

    Agent rejectAgent(Long agentId);
   // Double getAgentRevenue(Long agentId);

  //  Double getTotalRevenue();
    SuperAgentDashboardDTO getDashboard();
    Agent updateAgent(Long agentId, UpdateAgent request);
   void deleteAgent(Long agentId);

    //cliams

/*List<Claim> getAllClaims();

        Claim assignClaimToAgent(Long claimId,
                                 Long agentId);

        Claim approveClaim(Long claimId);

        Claim rejectClaim(Long claimId,
                          String rejectionReason);

        Claim settleClaim(Long claimId,
                          Double settlementAmount); */




}