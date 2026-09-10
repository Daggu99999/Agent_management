package AgentManagement.Controller;

import AgentManagement.DTO.UpdateProfileRequest;
import AgentManagement.Entity.Agent;
import AgentManagement.Entity.InsuranceProduct;
import AgentManagement.Entity.Policy;
import AgentManagement.Repository.AgentRepository;
import AgentManagement.Service.AgentService;
import AgentManagement.Service.InsuranceProductService;
import AgentManagement.Service.PaymentService;
import AgentManagement.Service.PolicyService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {

    private final AgentService agentService;
    private final InsuranceProductService insuranceProductService;
    private final PolicyService policyService;
    private final AgentRepository agentRepository;
    private final PaymentService paymentService;


    public HomeController(AgentService agentService,
                          InsuranceProductService insuranceProductService, PolicyService policyService,
                          AgentRepository agentRepository, PaymentService paymentService
                          ) {
        this.agentService = agentService;
        this.insuranceProductService=insuranceProductService;

        this.policyService = policyService;
        this.agentRepository = agentRepository;
        this.paymentService = paymentService;

    }

    @GetMapping("/")
    public String home() {
        return "index";
    }


    //main page login to display login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/register")
    public String registerPage() {
        return "registrationpage";
    }

    @PostMapping("/register/{superAgentId}")
    public String registerAgent(
            Agent agent,
            @PathVariable Long superAgentId) {

        agentService.registerAgent(agent, superAgentId);

        return "redirect:/";
    }

    //policy creation

    @PostMapping("/customer/buy")
    public String buyPolicy(
            @RequestParam Long productId,
            RedirectAttributes redirectAttributes) {

        Agent customer = agentRepository
                .findByEmail("rony@gmail.com")
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Policy policy = policyService.createPolicy(
                customer.getId(),
                productId);

        redirectAttributes.addFlashAttribute(
                "policy",
                policy);

        return "redirect:/payment";
    }

    @GetMapping("/payment")
    public String paymentPage() {
        return "payment";
    }



    //user interface
        @GetMapping("/user-interface")
        public String userInterface() {
            return "user-interface";
        }


    // Dashboard
    @GetMapping("/customer/dashboard")
    public String customerDashboard() {
        return "customer-dashboard";
    }

    // Insurance Products
    @GetMapping("/customer/products")
    public String viewProducts(Model model) {

        List<InsuranceProduct> products =
                insuranceProductService.getAllProducts();

        model.addAttribute("products", products);

        return "insurance-products";
    }

    //create policy


    // Policies
    @GetMapping("/customer/policies")
    public String myPolicies(
            Model model) {

        Long customerId = 10L;

        model.addAttribute(
                "policies",
                policyService.getPoliciesByCustomerId(
                        customerId));

        return "policies";
    }

    // Policy Benefits
    @GetMapping("/customer/policy-benefits")
    public String policyBenefits() {
        return "policy-benefits";
    }

    // Claims
    @GetMapping("/customer/claims")
    public String claims() {
        return "claims";
    }




    @GetMapping("/customer/payments")
    public String myPayments(Model model) {

        Long customerId = 10L;

        model.addAttribute(
                "payments",
                paymentService.getPaymentsByCustomerId(customerId)
        );

        return "my-payments";
    }

    // Profile
    @GetMapping("/customer/profile")
    public String profile(Model model) {

        Long userId = 10L; // temporary

        model.addAttribute(
                "user",
                agentService.getProfile(userId));

        return "profile";
    }

    @PostMapping("/customer/update-profile")
    public String updateProfile(
            UpdateProfileRequest request) {

        Long userId = 10L; // temporary

        agentService.updateProfile(
                userId,
                request);

        return "redirect:/customer/profile";
    }

    @GetMapping("/customer/edit-profile")
    public String editProfile(Model model) {

        Long userId = 10L; // temporary

        model.addAttribute(
                "user",
                agentService.getProfile(userId));

        return "edit-profile";
    }

    //index
    @GetMapping("/index")
    public String dashboard() {
        return "index";
    }
}