package isel.sisinf.jpa;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    private String manager;
    private String address;
    private String city;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Bicycle> bicycles;

    // Getters and setters
}