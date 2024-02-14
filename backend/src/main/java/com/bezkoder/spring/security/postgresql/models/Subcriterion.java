package com.bezkoder.spring.security.postgresql.models;
import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "subcriteria")
public class Subcriterion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Otros atributo

    // Getters y Setters
}
