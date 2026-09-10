package AgentManagement.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "revenue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Revenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long revenueId;

    private Double amount;

}