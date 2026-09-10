package AgentManagement.DTO;

import AgentManagement.Entity.Status;
import lombok.Data;

@Data
public class UpdateAgent {

    private String name;
    private String email;
    private String mobileNumber;
    private Status status;
}