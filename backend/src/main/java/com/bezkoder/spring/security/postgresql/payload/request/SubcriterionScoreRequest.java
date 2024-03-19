package com.bezkoder.spring.security.postgresql.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
public class SubcriterionScoreRequest {
    private Long projectEvaluation;
    private Long subcriterion;
    private Double score;

    // Getters y Setters
}

