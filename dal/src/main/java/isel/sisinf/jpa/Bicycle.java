package isel.sisinf.jpa;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Bicycles")
@NamedQuery(name="Bicycle.findByKey",
        query="SELECT b FROM Bicycle b WHERE b.bicycle_id =:key")
@NamedStoredProcedureQuery(
        name = "is_bike_available",
        procedureName = "is_bike_available",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Timestamp.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class)
        }
)
public class Bicycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bicycle_id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private String type;
    private int weight;
    private String model;
    private String brand;
    private int gear_system;
    private String status;
    private Integer range;  // Only for electric bicycles
    private Integer max_speed;  // Only for electric bicycles

    @OneToOne(mappedBy = "bicycle", cascade = CascadeType.ALL)
    private GPSDevice gps_device;

    @OneToMany(mappedBy = "bicycle", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    // Getters and setters

    public String toString() {
        return "Bicycle{" +
                "bicycle_id=" + bicycle_id +
                ", shop=" + shop +
                ", type='" + type + '\'' +
                ", weight=" + weight +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", gear_system=" + gear_system +
                ", status='" + status + '\'' +
                ", range=" + range +
                ", max_speed=" + max_speed +
                ", gps_device=" + gps_device +
                ", reservations=" + reservations +
                '}';
    }
}

