package dk.au.standservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "stands")
public class Stand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Customer number is required")
    @Column(name = "customer_number", nullable = false)
    @JsonProperty("customerNumber")
    private String customerNumber;

    @NotNull(message = "Square metres is required")
    @Positive(message = "Square metres must be greater than 0")
    @Column(name = "square_metres", nullable = false)
    @JsonProperty("squareMetres")
    private Double squareMetres;

    @NotBlank(message = "Fair is required")
    @Column(name = "fair", nullable = false)
    @JsonProperty("fair")
    private String fair;

    @NotBlank(message = "Location is required")
    @Column(name = "location", nullable = false)
    @JsonProperty("location")
    private String location;

    public Stand() {}

    public Stand(String customerNumber, Double squareMetres, String fair, String location) {
        this.customerNumber = customerNumber;
        this.squareMetres = squareMetres;
        this.fair = fair;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Stand{" +
                "id=" + id +
                ", customerNumber='" + customerNumber + '\'' +
                ", squareMetres=" + squareMetres +
                ", fair='" + fair + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Double getSquareMetres() {
        return squareMetres;
    }

    public void setSquareMetres(Double squareMetres) {
        this.squareMetres = squareMetres;
    }

    public String getFair() {
        return fair;
    }

    public void setFair(String fair) {
        this.fair = fair;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
} 