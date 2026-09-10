package AgentManagement.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "agents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "super_agent_id")
    private SuperAgent superAgent;

    @OneToMany(mappedBy = "agent")
    private List<Policy> createdPolicies;

    @OneToMany(mappedBy = "customer")
    private List<Policy> purchasedPolicies;

    @OneToMany(mappedBy = "customer")
    private List<Payment> payments;

    @OneToMany(mappedBy = "agent")
    private List<Claim> assignedClaims;

    @OneToMany(mappedBy = "customer")
    private List<Claim> customerClaims;



}
