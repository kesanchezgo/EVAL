package com.bezkoder.spring.security.postgresql.models;
import java.util.*;
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
@Table(name = "criteria")
public class Criterion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String name; // Variable for criterion name

    @Column(columnDefinition = "TEXT")
    private String description; // Variable for criterion description

    private double weight; // Variable for criterion weight (percentage)
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "subcriteria_criteria", joinColumns = @JoinColumn(name = "criteria_id"), inverseJoinColumns = @JoinColumn(name = "subcriteria_id"))
    private Set<Subcriterion> subcriterias = new HashSet<>();


    // Getters y Setters
}

