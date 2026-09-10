package AgentManagement.DTO;

import AgentManagement.Entity.PolicyStatus;
import AgentManagement.Entity.PolicyType;
import lombok.Data;

@Data
public class UpdatePolicyRequest {

    private PolicyType policyType;
    private Double premiumAmount;
    private Double coverageAmount;
    private PolicyStatus status;
}
