package isel.sisinf.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "GPS_Devices")
public class GPSDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gps_id;

    @OneToOne
    @JoinColumn(name = "bicycle_id")
    private Bicycle bicycle;

    private String serial_number;
    private double latitude;
    private double longitude;
    private double battery_percentage;

    // Getters and setters
}
