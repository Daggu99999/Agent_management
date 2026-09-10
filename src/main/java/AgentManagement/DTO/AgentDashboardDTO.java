package AgentManagement.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgentDashboardDTO {

    private Long agentId;

    private String agentName;

    private Long todayPolicies;

    private Long weeklyPolicies;

    private Long monthlyPolicies;

    private Double totalRevenue;
}