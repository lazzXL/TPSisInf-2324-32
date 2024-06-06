package isel.sisinf.jpa;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shop_id;

    private String manager;
    private String address;
    private String city;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Bicycle> bicycles;

    // Getters and setters
    public String toString () {
        return "Shop{" +
                "shop_id=" + shop_id +
                ", manager='" + manager + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", bicycles=" + bicycles +
                '}';
    }
}