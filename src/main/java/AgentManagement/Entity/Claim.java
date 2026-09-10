package AgentManagement.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "claims")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    private Double claimAmount;

    private String claimReason;

    private String rejectionReason;

    private Double settlementAmount;

    private LocalDateTime claimDate;

    private LocalDateTime approvalDate;

    private LocalDateTime settlementDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Agent customer;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;


}