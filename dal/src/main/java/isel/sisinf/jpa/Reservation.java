package isel.sisinf.jpa;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bicycle_id")
    private Bicycle bicycle;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double amount;

    // Getters and setters
}
