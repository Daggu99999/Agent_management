package AgentManagement.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "policy_benefits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long benefitId;

    private String benefitName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private InsuranceProduct insuranceProduct;
}