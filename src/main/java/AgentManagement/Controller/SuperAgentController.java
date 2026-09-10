package AgentManagement.Controller;

import AgentManagement.DTO.SuperAgentDashboardDTO;
import AgentManagement.DTO.UpdateAgent;
import AgentManagement.Entity.Agent;
import AgentManagement.Entity.Claim;
import AgentManagement.Entity.Payment;
import AgentManagement.Entity.SuperAgent;
import AgentManagement.Service.SuperAgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/superagent")
public class SuperAgentController {

    private final SuperAgentService superAgentService;

    public SuperAgentController(SuperAgentService superAgentService) {
        this.superAgentService = superAgentService;
    }

    @PreAuthorize("hasRole('SUPER_AGENT')")
    @PostMapping
    public SuperAgent createSuperAgent(@RequestBody SuperAgent superAgent) {
        return superAgentService.createSuperAgent(superAgent);
    }

    @PreAuthorize("hasRole('SUPER_AGENT')")
    @GetMapping
    public List<SuperAgent> getAllSuperAgents() {
        return superAgentService.getAllSuperAgents();
    }

    @PreAuthorize("hasRole('SUPER_AGENT')")
    @GetMapping("/{id}")
    public SuperAgent getSuperAgentById(@PathVariable Long id) {
        return superAgentService.getSuperAgentById(id);
    }

    @PreAuthorize("hasRole('SUPER_AGENT')")
    @PutMapping("/agent/{agentId}/approve")
    public Agent approveAgent(@PathVariable Long agentId) {
        return superAgentService.approveAgent(agentId);
    }

    @PreAuthorize("hasRole('SUPER_AGENT')")
    @PutMapping("/agent/{agentId}/reject")
    public Agent rejectAgent(@PathVariable Long agentId) {
        return superAgentService.rejectAgent(agentId);
    }

  /*  @PreAuthorize("hasRole('SUPER_AGENT')")
    @GetMapping("/revenue/agent/{agentId}")
    public Double getAgentRevenue(
            @PathVariable Long agentId) {

        return superAgentService.getAgentRevenue(agentId);
    }

    @PreAuthorize("hasRole('SUPER_AGENT')")
    @GetMapping("/revenue/total")
    public Double getTotalRevenue() {

        return superAgentService.getTotalRevenue();
    }
*/
    //superagentDashboard
    @PreAuthorize("hasRole('SUPER_AGENT')")
    @GetMapping("/dashboard")
    public SuperAgentDashboardDTO getDashboard() {

        return superAgentService.getDashboard();
    }
    // agentUpdate
    @PutMapping("/upadte/{agentId}")
    public Agent updateAgent(
            @PathVariable Long agentId,
            @RequestBody UpdateAgent request) {

        return superAgentService.updateAgent(agentId, request);
    }
    //delete agent
    @DeleteMapping("/delete/{agentId}")
    public void deleteAgent(
            @PathVariable Long agentId) {

         superAgentService.deleteAgent(agentId);
    }



}