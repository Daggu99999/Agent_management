package AgentManagement.Controller;

import AgentManagement.ServiceImpl.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController{

    private final EmailService emailService;

    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @GetMapping("/send-mail")
    public String sendMail() {

        emailService.sendEmail(
                "daggumahesh510@gmail.com",
                "Insurance Test Mail",
                "Mail sent successfully from Spring Boot");

        return "Mail Sent";
    }
}