package AgentManagement.Repository;

import AgentManagement.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByCustomer_Id(Long customerId);

    @Query("SELECT COALESCE(SUM(p.amount), 0.0) FROM Payment p " +
            "WHERE p.status = 'SUCCESS' AND p.paymentDate >= :startDate")
    Double calculateRevenueSince(@Param("startDate") LocalDateTime startDate);

}