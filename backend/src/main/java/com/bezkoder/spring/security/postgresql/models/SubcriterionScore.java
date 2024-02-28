package com.bezkoder.spring.security.postgresql.models;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "subcriterion_scores")
public class SubcriterionScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "evaluation_id", referencedColumnName = "evaluation_id"),
        @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    })
    private ProjectEvaluation projectEvaluation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcriteria_id")
    private Subcriterion subcriterion;

    private Double score; // Evaluation score for this project

}
