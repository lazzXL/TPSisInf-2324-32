package isel.sisinf.jpa;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Bicycles")
public class Bicycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bicycleId;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private String type;
    private int weight;
    private String model;
    private String brand;
    private int gearSystem;
    private String status;
    private Integer range;  // Only for electric bicycles
    private Integer maxSpeed;  // Only for electric bicycles

    @OneToOne(mappedBy = "bicycle", cascade = CascadeType.ALL)
    private GPSDevice gpsDevice;

    @OneToMany(mappedBy = "bicycle", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    // Getters and setters
}

