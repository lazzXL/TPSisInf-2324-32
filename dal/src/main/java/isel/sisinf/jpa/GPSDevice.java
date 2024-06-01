package isel.sisinf.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "GPS_Devices")
public class GPSDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gpsId;

    @OneToOne
    @JoinColumn(name = "bicycle_id")
    private Bicycle bicycle;

    private String serialNumber;
    private double latitude;
    private double longitude;
    private double batteryPercentage;

    // Getters and setters
}
