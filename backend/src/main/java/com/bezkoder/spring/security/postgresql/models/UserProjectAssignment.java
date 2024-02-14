package com.bezkoder.spring.security.postgresql.models;
import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "user_project_assignments")
public class UserProjectAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Otros atributos

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    // Getters y Setters
}
