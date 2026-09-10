package AgentManagement.Service;

import AgentManagement.Entity.Payment;
import AgentManagement.Entity.PaymentType;

import java.util.List;

public interface PaymentService {

    Payment makePayment(
            Long policyId,
            PaymentType paymentType);

    List<Payment> getPaymentsByCustomerId(Long customerId);

}