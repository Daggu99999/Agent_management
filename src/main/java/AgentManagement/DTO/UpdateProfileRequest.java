package AgentManagement.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {

    private String firstName;
    private String lastName;
    private String mobileNumber;
}