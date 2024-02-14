package com.bezkoder.spring.security.postgresql.models;
import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "criteria")
public class Criterion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Otros atributos
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "subcriteria_criteria", joinColumns = @JoinColumn(name = "criteria_id"), inverseJoinColumns = @JoinColumn(name = "subcriteria_id"))
    private Set<Subcriterion> subcriterias = new HashSet<>();


    // Getters y Setters
}

