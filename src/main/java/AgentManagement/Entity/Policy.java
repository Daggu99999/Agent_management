package AgentManagement.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;

    @Enumerated(EnumType.STRING)
    private PolicyType policyType;
    private Double premiumAmount;

    private Double coverageAmount;

    private LocalDateTime createdDate;
    private LocalDateTime rejectedDate;
    private LocalDateTime approvedDate;

    @Enumerated(EnumType.STRING)
    private PolicyStatus status;


    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Agent customer;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private InsuranceProduct insuranceProduct;

}