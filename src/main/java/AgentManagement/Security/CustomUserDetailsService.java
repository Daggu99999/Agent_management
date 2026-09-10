package AgentManagement.Security;

import AgentManagement.Entity.Agent;
import AgentManagement.Entity.SuperAgent;
import AgentManagement.Repository.AgentRepository;
import AgentManagement.Repository.SuperAgentRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AgentRepository agentRepository;
    private final SuperAgentRepository superAgentRepository;

    public CustomUserDetailsService(
            AgentRepository agentRepository,
            SuperAgentRepository superAgentRepository) {

        this.agentRepository = agentRepository;
        this.superAgentRepository = superAgentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Agent agent = agentRepository.findByEmail(email).orElse(null);

        if (agent != null) {

            return new User(
                    agent.getEmail(),
                    agent.getPassword(),
                    List.of(
                            new SimpleGrantedAuthority(
                                    agent.getRole().name()))
            );
        }

        SuperAgent superAgent =
                superAgentRepository.findByEmail(email).orElse(null);

        if (superAgent != null) {

            return new User(
                    superAgent.getEmail(),
                    superAgent.getPassword(),
                    List.of(
                            new SimpleGrantedAuthority(
                                    superAgent.getRole().name()))
            );
        }

        throw new UsernameNotFoundException(
                "User not found");
    }
}

