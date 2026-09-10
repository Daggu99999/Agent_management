package AgentManagement.Controller;

import AgentManagement.Entity.*;
import AgentManagement.Repository.*;
import AgentManagement.Service.AgentService;
import AgentManagement.Service.SuperAgentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/superagent")
public class SuperAgentWebController {
    private final AgentService agentService;
    private final AgentRepository agentRepository;
    private final SuperAgentService superAgentService;
    private final InsuranceProductRepository insuranceProductRepository;
    private final PolicyRepository policyRepository;
    private final ClaimRepository claimRepository;
    private final PaymentRepository paymentRepository;

    public SuperAgentWebController(AgentService agentService,
                                   AgentRepository agentRepository, SuperAgentService superAgentService, InsuranceProductRepository insuranceProductRepository, PolicyRepository policyRepository, ClaimRepository claimRepository, ClaimRepository claimRepository1, PaymentRepository paymentRepository) {
        this.agentService = agentService;
        this.agentRepository = agentRepository;
        this.superAgentService = superAgentService;
        this.insuranceProductRepository = insuranceProductRepository;
        this.policyRepository = policyRepository;

        this.claimRepository = claimRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard() {

        return "superagent/dashboard";
    }

    //agentManagement
    @GetMapping("/agents")
    public String viewAgents(Model model) {

        model.addAttribute(
                "agents",
                agentRepository.findByRole(Role.ROLE_AGENT)
        );

        return "superAgent/view-agents";
    }

    @GetMapping("/agents/create")
    public String showCreateForm(Model model) {
        // Essential: th:object="${agent}" in HTML binds directly to this instance
        model.addAttribute("agent", new Agent());
        return "superagent/create-agent"; // Name of your form HTML file (create-agent.html)
    }

    @PostMapping("/agents/create")
    public String saveAgent(@ModelAttribute("agent") Agent agent) {
        // Enforce default configurations before database insertion
        agent.setRole(Role.ROLE_AGENT);
        agent.setStatus(Status.ACTIVE);

        // Save the entity using JPA
        agentRepository.save(agent);

        // Redirect back to the central Agent Management table page
        return "redirect:/api/superagent/agents";
    }


    // 1. Fetch the agent data and show the edit form
    @GetMapping("/agents/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid agent Id:" + id));

        model.addAttribute("agent", agent);
        return "superagent/edit-agent"; // Maps to edit-agent.html
    }

    // 2. Process the updates safely without touching the password
    @PostMapping("/agents/update/{id}")
    public String updateAgent(@PathVariable("id") Long id, @ModelAttribute("agent") Agent formAgent) {
        // Fetch the original entity record from the database to retain its password
        Agent existingAgent = agentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid agent Id:" + id));

        // Map only the non-sensitive fields from the submitted form data
        existingAgent.setFirstName(formAgent.getFirstName());
        existingAgent.setLastName(formAgent.getLastName());
        existingAgent.setEmail(formAgent.getEmail());
        existingAgent.setMobileNumber(formAgent.getMobileNumber());

        // Do NOT call existingAgent.setPassword(...) here.
        // This leaves the original password intact in the database.

        agentRepository.save(existingAgent);
        return "redirect:/api/superagent/agents";
    }


    @GetMapping("/agents/delete/{id}")
    public String deleteAgent(@PathVariable Long id) {

        superAgentService.deleteAgent(id);

        return "redirect:/api/superagent/view-agents";
    }

    @GetMapping("/agents/approve/{id}")
    public String approveAgent(
            @PathVariable Long id) {

        superAgentService.approveAgent(id);

        return "redirect:/api/superagent/agents";
    }

    @GetMapping("/agents/reject/{id}")
    public String rejectAgent(
            @PathVariable Long id) {

        superAgentService.rejectAgent(id);

        return "redirect:/api/superagent/agents";
    }

    //customer
    @GetMapping("/customers")
    public String viewCustomers(Model model) {

        model.addAttribute(
                "customers",
                agentRepository.findByRole(Role.ROLE_CUSTOMER));

        return "superagent/view-customers";
    }

    @GetMapping("/customers/activate/{id}")
    public String activateCustomer(
            @PathVariable Long id) {

        Agent customer =
                agentRepository.findById(id)
                        .orElseThrow();

        customer.setStatus(Status.ACTIVE);

        agentRepository.save(customer);

        return "redirect:/api/superagent/customers";
    }

    @GetMapping("/customers/deactivate/{id}")
    public String deactivateCustomer(
            @PathVariable Long id) {

        Agent customer =
                agentRepository.findById(id)
                        .orElseThrow();

        customer.setStatus(Status.INACTIVE);

        agentRepository.save(customer);

        return "redirect:/api/superagent/customers";
    }

    @GetMapping("/customers/change-role/{id}")
    public String changeRole(
            @PathVariable Long id) {

        Agent user =
                agentRepository.findById(id)
                        .orElseThrow();

        if(user.getRole() == Role.ROLE_CUSTOMER) {

            user.setRole(Role.ROLE_AGENT);

        } else {

            user.setRole(Role.ROLE_CUSTOMER);
        }

        agentRepository.save(user);

        return "redirect:/api/superagent/customers";
    }




//product
@GetMapping("/products")
public String viewProducts(Model model) {

    model.addAttribute(
            "products",
            insuranceProductRepository.findAll());

    return "superAgent/view-products";
}

    @GetMapping("/products/create")
    public String createProductForm(Model model) {

        model.addAttribute(
                "product",
                new InsuranceProduct());

        return "superAgent/create-product";
    }

    @PostMapping("/products/create")
    public String createProduct(
            @ModelAttribute InsuranceProduct product) {

        product.setActive(true);

        insuranceProductRepository.save(product);

        return "redirect:/api/superagent/products";
    }



    //premium manage
    @GetMapping("/products/premium/{id}")
    public String premiumPage(
            @PathVariable Long id,
            Model model) {

        InsuranceProduct product =
                insuranceProductRepository.findById(id)
                        .orElseThrow();

        model.addAttribute("product", product);

        return "superAgent/manage-premium";
    }
    @GetMapping("/products/edit/{id}")
    public String editProductPage(@PathVariable Long id) {
        return "superagent/edit-product";
    }

    @PostMapping("/products/premium/update/{id}")
    public String updatePremium(
            @PathVariable Long id,
            @RequestParam Double basePremium) {

        InsuranceProduct product =
                insuranceProductRepository.findById(id)
                        .orElseThrow();

        product.setBasePremium(basePremium);

        insuranceProductRepository.save(product);

        return "redirect:/api/superagent/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return "redirect:/superagent/products";
    }
//policy benefits

// policy
@GetMapping("/policies")
public String listPolicies(Model model) {
    model.addAttribute("policies", policyRepository.findAll());
    return "superagent/view-policies";
}
    // 2. Approve Policy Endpoint
    @GetMapping("/policies/approve/{id}")
    public String approvePolicy(@PathVariable("id") Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid policy Id:" + id));

        policy.setStatus(PolicyStatus.APPROVED); // Assuming your enum has APPROVED
        policy.setApprovedDate(LocalDateTime.now());
        policy.setRejectedDate(null); // Clear previous rejections if any

        policyRepository.save(policy);
        return "redirect:/api/superagent/policies";
    }

    // 3. Reject Policy Endpoint
    @GetMapping("/policies/reject/{id}")
    public String rejectPolicy(@PathVariable("id") Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid policy Id:" + id));

        policy.setStatus(PolicyStatus.REJECTED); // Assuming your enum has REJECTED
        policy.setRejectedDate(LocalDateTime.now());
        policy.setApprovedDate(null); // Clear previous approvals if any

        policyRepository.save(policy);
        return "redirect:/api/superagent/policies";
    }

    // 4. Show Policy Edit Form
    @GetMapping("/policies/edit/{id}")
    public String showEditPolicyForm(@PathVariable("id") Long id, Model model) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid policy Id:" + id));

        model.addAttribute("policy", policy);
        return "superagent/edit-policy";
    }

    // 5. Process Policy Field Updates
    @PostMapping("/policies/update/{id}")
    public String updatePolicy(@PathVariable("id") Long id, @ModelAttribute("policy") Policy formPolicy) {
        Policy existingPolicy = policyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid policy Id:" + id));

        // Apply changes from form input fields safely
        existingPolicy.setPremiumAmount(formPolicy.getPremiumAmount());
        existingPolicy.setCoverageAmount(formPolicy.getCoverageAmount());

        policyRepository.save(existingPolicy);
        return "redirect:/api/superagent/policies";
    }

    //claims
    // 1. View all claims
    @GetMapping("/claims")
    public String viewAllClaims(Model model) {
        model.addAttribute("claims", claimRepository.findAll());
        return "superagent/view-claims";
    }

    // 2. Assign Claim to a random Active/Approved Agent



    // 3. Approve Claim
    @GetMapping("/claims/approve/{id}")
    public String approveClaim(@PathVariable("id") Long claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid claim Id:" + claimId));

        claim.setStatus(ClaimStatus.APPROVED); // Ensure APPROVED is in your ClaimStatus enum
        claim.setApprovalDate(LocalDateTime.now());
        claim.setRejectionReason(null); // Clear previous rejections if any

        claimRepository.save(claim);
        return "redirect:/api/superagent/claims";
    }

    // 4. Reject Claim
    @PostMapping("/claims/reject/{id}")
    public String rejectClaim(@PathVariable("id") Long claimId, @RequestParam("rejectionReason") String reason) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid claim Id:" + claimId));

        claim.setStatus(ClaimStatus.REJECTED); // Ensure REJECTED is in your ClaimStatus enum
        claim.setRejectionReason(reason);
        claim.setApprovalDate(null);

        claimRepository.save(claim);
        return "redirect:/api/superagent/claims";
    }

    // 5. Settle Claim (Only if already approved)
    @GetMapping("/claims/assign/{id}")
    public String assignRandomApprovedAgent(@PathVariable("id") Long claimId, RedirectAttributes redirectAttributes) {
        // 1. Locate the claim record
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid claim Id:" + claimId));

        // 2. Fetch all agents matching the exact functional role
        List<Agent> allAgents = agentRepository.findByRole(Role.ROLE_AGENT);

        // 3. FIX: Filter down strictly by APPROVED status (Ignoring ACTIVE or other states)
        List<Agent> approvedAgents = allAgents.stream()
                .filter(agent -> agent.getStatus() == Status.APPROVED) // Matches your exact Approved status enum value
                .collect(Collectors.toList());

        // 4. Validation: Ensure the approved pool is not empty
        if (approvedAgents.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Assignment Failed: No agents with an APPROVED status are currently available in the system.");
            return "redirect:/api/superagent/claims";
        }

        // 5. Random Picker Logic: Shuffle the list and select the first index record
        java.util.Collections.shuffle(approvedAgents);
        Agent selectedRandomAgent = approvedAgents.get(0);

        // 6. Bind the randomized approved agent to the claim record
        claim.setAgent(selectedRandomAgent);

        // 7. Safety State Management: Keep status as APPROVED if already approved, otherwise progress to ASSIGNED
        if (claim.getStatus() != ClaimStatus.APPROVED) {
            claim.setStatus(ClaimStatus.ASSIGNED);
        }

        claimRepository.save(claim);

        // 8. Flash success notification message to the UI dashboard
        redirectAttributes.addFlashAttribute("successMessage",
                "Success! Approved Agent " + selectedRandomAgent.getFirstName() + " " + selectedRandomAgent.getLastName() + " has been randomly assigned to Claim #" + claimId);

        return "redirect:/api/superagent/claims";
    }

    //settle claim
    @GetMapping("/claims/settle/{id}")
    public String settleClaim(
            @PathVariable("id") Long claimId) {

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid claim Id: " + claimId));

        claim.setStatus(ClaimStatus.SETTLED);

        claim.setSettlementAmount(
                claim.getClaimAmount());

        claim.setSettlementDate(
                LocalDateTime.now());

        claimRepository.save(claim);

        return "redirect:/api/superagent/claims";
    }


    //payments

    // 1. View all system payments dashboard
    @GetMapping("/payments")
    public String viewAllPayments(Model model) {
        model.addAttribute("payments", paymentRepository.findAll());
        return "superagent/view-payments";
    }

    // 2. Process payment refund operation with dynamic reason
    @PostMapping("/payments/refund/{id}")
    public String processPaymentRefund(@PathVariable("id") Long paymentId,
                                       @RequestParam("refundReason") String reason,
                                       RedirectAttributes redirectAttributes) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment Id:" + paymentId));

        // Optional Validation: Only allow refunding if payment is SUCCESSFUL/COMPLETED
        // Adjust 'PaymentStatus.SUCCESSFUL' or 'PaymentStatus.COMPLETED' based on your exact enum properties
        if (payment.getStatus().toString().equals("REFUNDED")) {
            redirectAttributes.addFlashAttribute("errorMessage", "This payment has already been refunded.");
            return "redirect:/api/superagent/payments";
        }

        // Apply structural mutation values
        payment.setStatus(PaymentStatus.REFUNDED); // Ensure REFUNDED exists in your PaymentStatus enum
        payment.setRefundDate(LocalDateTime.now());
        payment.setRefundReason(reason);

        paymentRepository.save(payment);

        redirectAttributes.addFlashAttribute("successMessage", "Payment ID #" + paymentId + " has been successfully refunded.");
        return "redirect:/api/superagent/payments";
    }
// Dashboards  and analytics

    @GetMapping("/performance")
    public String showCustomerDashboard(Model model) {
        // Count totals filtering by Customer role
        long totalCustomers = agentRepository.countByRole(Role.ROLE_CUSTOMER);
        long activeCustomers = agentRepository.countByRoleAndStatus(Role.ROLE_CUSTOMER, Status.ACTIVE);
        long inactiveCustomers = agentRepository.countByRoleAndStatus(Role.ROLE_CUSTOMER, Status.INACTIVE);

        // Pass metrics to Thymeleaf
        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("activeCustomers", activeCustomers);
        model.addAttribute("inactiveCustomers", inactiveCustomers);

        // Returns template located at src/main/resources/templates/superagent/performance.html
        return "superagent/performance";
    }
  //Polciy dashbpard
  @GetMapping("/statistic")
  public String showPolicyDashboard(Model model) {
      // Fetch counts using uppercase status enums
      long pendingPolicies = policyRepository.countByStatus(PolicyStatus.PENDING);
      long approvedPolicies = policyRepository.countByStatus(PolicyStatus.APPROVED);
      long rejectedPolicies = policyRepository.countByStatus(PolicyStatus.REJECTED);

      // Pass attributes to Thymeleaf
      model.addAttribute("pendingPolicies", pendingPolicies);
      model.addAttribute("approvedPolicies", approvedPolicies);
      model.addAttribute("rejectedPolicies", rejectedPolicies);

      // Returns template located at src/main/resources/templates/superagent/statistic.html
      return "superagent/statistic";
  }

  // Revenue report


    @GetMapping("/revenue")
    public String showRevenueDashboard(Model model) {
        LocalDateTime now = LocalDateTime.now();

        // Start boundaries for timeframes
        LocalDateTime startOfDay = now.with(LocalTime.MIN);
        LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY).with(LocalTime.MIN);
        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        LocalDateTime startOfYear = now.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);

        // Fetch sums from repository
        Double dailyRevenue = paymentRepository.calculateRevenueSince(startOfDay);
        Double weeklyRevenue = paymentRepository.calculateRevenueSince(startOfWeek);
        Double monthlyRevenue = paymentRepository.calculateRevenueSince(startOfMonth);
        Double yearlyRevenue = paymentRepository.calculateRevenueSince(startOfYear);

        // Pass data to UI
        model.addAttribute("dailyRevenue", dailyRevenue);
        model.addAttribute("weeklyRevenue", weeklyRevenue);
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("yearlyRevenue", yearlyRevenue);

        // Returns templates/superagent/revenue.html
        return "superagent/revenue";



    }

    //claims Dashboard
    @GetMapping("/claimsDashboard")
    public String showClaimsDashboard(Model model) {
        // Fetch counts from repository for all status enums
        long submittedClaims = claimRepository.countByStatus(ClaimStatus.SUBMITTED);
        long assignedClaims = claimRepository.countByStatus(ClaimStatus.ASSIGNED);
        long underReviewClaims = claimRepository.countByStatus(ClaimStatus.UNDER_REVIEW);
        long approvedClaims = claimRepository.countByStatus(ClaimStatus.APPROVED);
        long rejectedClaims = claimRepository.countByStatus(ClaimStatus.REJECTED);
        long settledClaims = claimRepository.countByStatus(ClaimStatus.SETTLED);

        // Pass metrics to Thymeleaf
        model.addAttribute("submittedClaims", submittedClaims);
        model.addAttribute("assignedClaims", assignedClaims);
        model.addAttribute("underReviewClaims", underReviewClaims);
        model.addAttribute("approvedClaims", approvedClaims);
        model.addAttribute("rejectedClaims", rejectedClaims);
        model.addAttribute("settledClaims", settledClaims);

        // Returns template located at src/main/resources/templates/superagent/claims-dashboard.html
        return "superagent/claims-dashboard";
    }

}