package AgentManagement.Controller;

import AgentManagement.Entity.PaymentType;
import AgentManagement.Service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/customer/pay")
    public String makePayment(
            @RequestParam Long policyId,
            @RequestParam PaymentType paymentType) {

        paymentService.makePayment(
                policyId,
                paymentType);

        return "redirect:/customer/policies";
    }
}