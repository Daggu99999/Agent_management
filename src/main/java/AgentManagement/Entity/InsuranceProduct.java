package AgentManagement.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "insurance_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    @Enumerated(EnumType.STRING)
    private PolicyType policyType;

    private Double basePremium;

    private Double coverageAmount;

    private String description;

    private boolean active;

    @OneToMany(mappedBy = "insuranceProduct",
            cascade = CascadeType.ALL)
    private List<PolicyBenefit> benefits;
}