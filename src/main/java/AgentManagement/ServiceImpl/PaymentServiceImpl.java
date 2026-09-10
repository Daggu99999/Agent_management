package AgentManagement.ServiceImpl;

import AgentManagement.Entity.*;
import AgentManagement.Repository.PaymentRepository;
import AgentManagement.Repository.PolicyRepository;
import AgentManagement.Service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PolicyRepository policyRepository;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            PolicyRepository policyRepository) {

        this.paymentRepository = paymentRepository;
        this.policyRepository = policyRepository;
    }

    @Override
    public Payment makePayment(
            Long policyId,
            PaymentType paymentType) {

        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() ->
                        new RuntimeException("Policy not found"));

        Payment payment = new Payment();

        payment.setAmount(
                policy.getPremiumAmount());

        payment.setTransactionId(
                "TXN" + System.currentTimeMillis());

        payment.setPaymentDate(
                LocalDateTime.now());

        payment.setStatus(
                PaymentStatus.SUCCESS);

        payment.setPaymentType(
                paymentType);

        payment.setCustomer(
                policy.getCustomer());

        payment.setPolicy(policy);

        Payment savedPayment =
                paymentRepository.save(payment);

        policy.setStatus(
                PolicyStatus.APPROVED);

        policy.setApprovedDate(
                LocalDateTime.now());

        policyRepository.save(policy);

        return savedPayment;
    }

    @Override
    public List<Payment> getPaymentsByCustomerId(Long customerId) {

        return paymentRepository.findByCustomer_Id(customerId);
    }

}
