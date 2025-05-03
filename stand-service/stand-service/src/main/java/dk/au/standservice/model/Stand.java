package dk.au.standservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stands")
public class Stand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_number", nullable = false)
    private String customerNumber;

    @Column(name = "square_metres", nullable = false)
    private Double squareMetres;

    @Column(name = "fair", nullable = false)
    private String fair;

    @Column(name = "location", nullable = false)
    private String location;
} 