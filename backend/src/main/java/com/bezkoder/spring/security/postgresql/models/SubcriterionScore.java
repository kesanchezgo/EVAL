/* package com.bezkoder.spring.security.postgresql.models;
import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "subcriterion_scores")
public class SubcriterionScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id")
    private ProjectEvaluation projectEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcriterion_id")
    private Subcriterion subcriterion;

    private Double score; // Puntaje del subcriterio para esta evaluación de proyecto

    // Getters y Setters
}
 */


 /* package com.bezkoder.spring.security.postgresql.models;
import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "subcriteria_scores")
public class SubcriterionScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_evaluation_id")
    private ProjectEvaluation projectEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcriteria_id")
    private Subcriterion subcriteria;

    private Double score; */ // Puntaje de la evaluación para este proyecto

    /* @OneToMany(mappedBy = "projectEvaluation")
    private Set<SubcriterionScore> subcriterionScores = new HashSet<>(); */

  /*   @OneToMany(mappedBy = "projectEvaluation", cascade = CascadeType.ALL)
    private Set<SubcriterionScore> subcriterionScores; // Puntajes de subcriterios para esta evaluación de proyecto */
    // Getters y Setters
/* } */
