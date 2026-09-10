package AgentManagement.Controller;

import AgentManagement.DTO.AgentDashboardDTO;
import AgentManagement.DTO.ResetPasswordRequest;
import AgentManagement.Entity.Agent;
import AgentManagement.Service.AgentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/agent")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/register/{superAgentId}")
    public Agent registerAgent(
            @RequestBody Agent agent,
            @PathVariable Long superAgentId) {

        return agentService.registerAgent(agent, superAgentId);
    }

    @GetMapping("/getAllAgents")
    public List<Agent> getAllAgents() {
        return agentService.getAllAgents();
    }

    @PreAuthorize("hasRole('SUPER_AGENT')")
    @GetMapping("/{id}")
    public Agent getAgentById(@PathVariable Long id) {

        return agentService.getAgentById(id);
    }

   //@PreAuthorize("hasRole('AGENT')")
    @GetMapping("/dashboard/{agentId}")
    public AgentDashboardDTO getDashboard(
            @PathVariable Long agentId) {

        return agentService.getAgentDashboard(agentId);
    }

    //password reset
    @PutMapping("/{agentId}/reset-password")
    public String resetPassword(
            @PathVariable Long agentId,
            @RequestBody ResetPasswordRequest request) {

        return agentService.resetPassword(agentId, request);
    }
}