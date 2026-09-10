package AgentManagement.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Double amount;

    private String transactionId;

    private LocalDateTime paymentDate;

    private LocalDateTime refundDate;

    private String refundReason;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;


    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Agent customer;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;


}