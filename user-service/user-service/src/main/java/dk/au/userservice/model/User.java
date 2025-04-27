package dk.au.userservice.model;

import dk.au.userservice.model.PaymentOptions;
import dk.au.userservice.model.UserRole;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column
    private Date birth;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRole role;
    
    @Column
    private String adress;
    
    @Column
    private int phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column
    private PaymentOptions payment;

    public User() {}

    public User(String name, Date birth, UserRole role, String adress, int phoneNumber, PaymentOptions payment) {
        this.name = name;
        this.birth = birth;
        this.role = role;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PaymentOptions getPayment() {
        return payment;
    }

    public void setPayment(PaymentOptions payment) {
        this.payment = payment;
    }
}