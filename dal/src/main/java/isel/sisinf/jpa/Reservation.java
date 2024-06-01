package isel.sisinf.jpa;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reservations")
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

    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private double amount;

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

    public void setStartDate(LocalDateTime startDate) {
        this.start_date = startDate;
    }

    public LocalDateTime getStartDate() {
        return start_date;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.end_date = endDate;
    }

    public LocalDateTime getEndDate() {
        return end_date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }


}
