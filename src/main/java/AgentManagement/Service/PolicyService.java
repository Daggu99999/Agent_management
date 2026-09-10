package AgentManagement.Service;

import AgentManagement.DTO.PremiumRateRequest;
import AgentManagement.DTO.UpdatePolicyRequest;
import AgentManagement.Entity.Policy;

import java.util.List;

public interface PolicyService {

    Policy createPolicy(
                        Long customerId,
                        Long productId);

    List<Policy> getPoliciesByCustomerId(Long customerId);

   /* List<Policy> getAllPolicies();

    Policy getPolicyById(Long policyId);

    long getTodayApprovedPolicies(Long agentId);

    long getWeeklyApprovedPolicies(Long agentId);

    long getMonthlyApprovedPolicies(Long agentId);

    List<Policy> getActivePolicies();


    Policy updatePolicy(Long policyId,
                            UpdatePolicyRequest request);

    Policy updatePremiumRate(Long policyId,
                             PremiumRateRequest request);
*/
}
