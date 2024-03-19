package com.bezkoder.spring.security.postgresql.models;
import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String externalId;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String name;

    @Size(max = 255)
    private String area1;

    @Size(max = 255)
    private String area2;

    @NotBlank
    @Size(max = 1)
    private String status;

    @NotBlank
    @Size(max = 1)
    private String condition;

    /* @OneToMany(mappedBy = "project")
    private Set<ProjectEvaluation> projectEvaluations = new HashSet<>(); */

    // Otros atributos

/*     @ManyToMany(mappedBy = "projects")
    private Set<Evaluation> evaluations = new HashSet<>();
 */

    // Getters y Setters
}
