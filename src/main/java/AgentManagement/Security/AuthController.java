package AgentManagement.Security;

import AgentManagement.DTO.AuthResponse;
import AgentManagement.DTO.LoginRequest;
import AgentManagement.Entity.Agent;
import AgentManagement.Entity.SuperAgent;
import AgentManagement.Repository.AgentRepository;
import AgentManagement.Repository.SuperAgentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AgentRepository agentRepository;
    private final SuperAgentRepository superAgentRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            AgentRepository agentRepository,
            SuperAgentRepository superAgentRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder) {

        this.agentRepository = agentRepository;
        this.superAgentRepository = superAgentRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {

        Agent agent = agentRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if (agent != null &&
                passwordEncoder.matches(
                        request.getPassword(),
                        agent.getPassword())) {

            return new AuthResponse(
                    jwtUtil.generateToken(
                            agent.getEmail()));
        }

        SuperAgent superAgent = superAgentRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if (superAgent != null &&
                passwordEncoder.matches(
                        request.getPassword(),
                        superAgent.getPassword())) {

            return new AuthResponse(
                    jwtUtil.generateToken(
                            superAgent.getEmail()));
        }

        throw new RuntimeException(
                "Invalid Credentials");
    }
}