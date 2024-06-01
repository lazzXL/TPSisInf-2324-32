package isel.sisinf.jpa;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String name;
    private String address;
    private String email;
    private String phone;
    private String identificationNumber;
    private String nationality;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    // Getters and setters
}

