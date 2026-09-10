package AgentManagement.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuperAgentDashboardDTO {

    private Long totalAgents;

    private Long approvedAgents;

    private Long pendingAgents;

    private Long rejectedAgents;

    private Long totalPolicies;

    private Long approvedPolicies;

    private Long pendingPolicies;

    private Long rejectedPolicies;

    private Double totalRevenue;
}