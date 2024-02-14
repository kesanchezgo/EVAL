package com.bezkoder.spring.security.postgresql.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
@Entity
@Table(name = "subcriteria")
public class Subcriterion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String name; // Variable for criterion name

    @Column(columnDefinition = "TEXT")
    private String description; // Variable for criterion description

    private double weight; // Variable for criterion weight (percentage)

    // Otros atributo

    // Getters y Setters
}
