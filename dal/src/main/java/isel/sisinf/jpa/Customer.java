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
    private String identification_number;
    private String nationality;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    // Getters and setters

    public Long getCustomerId() {
        return customerId;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identification_number = identificationNumber;
    }

    public String getIdentificationNumber() {
        return identification_number;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }
}

