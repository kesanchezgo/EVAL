package com.bezkoder.spring.security.postgresql.models;
import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
@Entity
@Table(name = "evaluations")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Added numerical evaluation scale variable
    private Integer evaluationScale;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    // Otros atributos

    /* @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_evaluations", joinColumns = @JoinColumn(name = "evaluation_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> evaluators = new HashSet<>(); */


   /*  @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_evaluations", joinColumns = @JoinColumn(name = "evaluation_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects = new HashSet<>(); */


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "criteria_evaluations", joinColumns = @JoinColumn(name = "evaluation_id"), inverseJoinColumns = @JoinColumn(name = "criteria_id"))
    private Set<Criterion> criterions = new HashSet<>();

    // Getters y Setters
}
