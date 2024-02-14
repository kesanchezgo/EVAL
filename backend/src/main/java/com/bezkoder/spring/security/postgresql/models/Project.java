package com.bezkoder.spring.security.postgresql.models;
import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "project")
    private Set<ProjectEvaluation> projectEvaluations = new HashSet<>();

    // Otros atributos

/*     @ManyToMany(mappedBy = "projects")
    private Set<Evaluation> evaluations = new HashSet<>();
 */

    // Getters y Setters
}