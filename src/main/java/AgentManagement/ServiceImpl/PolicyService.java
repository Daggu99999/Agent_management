package AgentManagement.ServiceImpl;

import AgentManagement.Entity.Agent;
import AgentManagement.Entity.InsuranceProduct;
import AgentManagement.Entity.Policy;
import AgentManagement.Entity.PolicyStatus;
import AgentManagement.Entity.Role;
import AgentManagement.Entity.Status;
import AgentManagement.Repository.AgentRepository;
import AgentManagement.Repository.InsuranceProductRepository;
import AgentManagement.Repository.PolicyRepository;
import AgentManagement.Service.PolicyService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
class PolicyServiceImpl implements PolicyService {

    private final AgentRepository agentRepository;
    private final PolicyRepository policyRepository;
    private final InsuranceProductRepository insuranceProductRepository;

    public PolicyServiceImpl(
            AgentRepository agentRepository,
            PolicyRepository policyRepository,
            InsuranceProductRepository insuranceProductRepository) {

        this.agentRepository = agentRepository;
        this.policyRepository = policyRepository;
        this.insuranceProductRepository = insuranceProductRepository;
    }

    @Override
    public Policy createPolicy( Long customerId,
                               Long productId) {

        Agent customer = agentRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        if (customer.getRole() != Role.ROLE_CUSTOMER) {
            throw new RuntimeException(
                    "User is not a customer");
        }

        Agent agent = agentRepository.findAll()
                .stream()
                .filter(a -> a.getRole() == Role.ROLE_AGENT)
                .filter(a -> a.getStatus() == Status.APPROVED)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "No approved agent found"));

        InsuranceProduct product =
                insuranceProductRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        Policy policy = new Policy();

        policy.setAgent(agent);
        policy.setCustomer(customer);

        policy.setInsuranceProduct(product);

        policy.setPolicyType(
                product.getPolicyType());

        policy.setPremiumAmount(
                product.getBasePremium());

        policy.setCoverageAmount(
                product.getCoverageAmount());

        policy.setCreatedDate(
                LocalDateTime.now());

        policy.setStatus(
                PolicyStatus.PENDING);

        return policyRepository.save(policy);
    }

    //get policies based on id

    @Override
    public List<Policy> getPoliciesByCustomerId(Long customerId) {
        return policyRepository.findByCustomer_Id(customerId);
    }


}