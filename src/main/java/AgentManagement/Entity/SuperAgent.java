package AgentManagement.Entity;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "super_agents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuperAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @OneToMany(mappedBy = "superAgent",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Agent> agents;
}