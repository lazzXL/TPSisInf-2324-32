package isel.sisinf.model;
import jakarta.persistence.*;



import java.sql.Timestamp;

@Entity
@Table(name = "Reservations")
@NamedQuery(name="Reservation.findByKey",
        query="SELECT r FROM Reservation r WHERE r.reservation_id =:key")
@NamedStoredProcedureQuery(
        name = "makenewreservation",
        procedureName = "makenewreservation",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Timestamp.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Timestamp.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class),
        }
)

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservation_id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bicycle_id")
    private Bicycle bicycle;

    private Timestamp start_date;
    private Timestamp end_date;
    private double amount;

    @Version
    private long version;

    // Getters and setters

    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservation_id +
                ", shop=" + shop +
                ", customer=" + customer +
                ", bicycle=" + bicycle +
                ", startDate=" + start_date +
                ", endDate=" + end_date +
                ", amount=" + amount +
                '}';
    }

    public Long getReservationId() {
        return reservation_id;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Shop getShop() {
        return shop;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getVersion() {
        return version;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setStartDate(Timestamp startDate) {
        this.start_date = startDate;
    }

    public Timestamp getStartDate() {
        return start_date;
    }

    public void setEndDate(Timestamp endDate) {
        this.end_date = endDate;
    }

    public Timestamp getEndDate() {
        return end_date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }


}
