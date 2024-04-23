package com.bezkoder.spring.security.postgresql.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
@Entity
@Table(name = "project_evaluations")
@JsonIgnoreProperties({"evaluation"})
public class ProjectEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    private Double score; // Puntaje de la evaluación para este proyecto

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;


  /*   @OneToMany(mappedBy = "projectEvaluation")
    private Set<SubcriterionScore> subcriterionScores = new HashSet<>(); */


}
