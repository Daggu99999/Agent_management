package AgentManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClaimDashboard {

        private long totalCustomers;

        private long activePolicies;

        private long totalClaims;

        private long approvedClaims;

        private long rejectedClaims;

        private long settledClaims;
    }

