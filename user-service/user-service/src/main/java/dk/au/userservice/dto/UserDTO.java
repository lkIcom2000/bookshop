package dk.au.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Date;

@Schema(description = "Data Transfer Object for User information")
public class UserDTO {
    @Schema(description = "ID of the user")
    private Long id;

    @Schema(description = "Name of the user", example = "Max Mustermann")
    private String name;

    @Schema(description = "Birth date of the user", example = "2025-05-15")
    private Date birth;

    @Schema(
        description = "Role of the user (case-sensitive)",
        example = "USER",
        allowableValues = {"ADMIN", "USER", "GUEST"}
    )
    private String role;

    @Schema(description = "Address of the user", example = "Birk Centerpark 120")
    private String adress;

    @Schema(description = "Phone number of the user", example = "1234567")
    private int phoneNumber;

    @Schema(
        description = "Preferred payment method (case-sensitive)",
        example = "PAYPAL",
        allowableValues = {"PAYPAL", "SMSPAY", "CREDITCARD"}
    )
    private String payment;

    public UserDTO(Long id, String name, Date birth, String role, String adress, int phoneNumber, String payment) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.role = role;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.payment = payment;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                ", role='" + role + '\'' +
                ", adress='" + adress + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", payment='" + payment + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}