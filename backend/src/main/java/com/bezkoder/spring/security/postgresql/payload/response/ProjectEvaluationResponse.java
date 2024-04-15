package com.bezkoder.spring.security.postgresql.payload.response;

import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
public class ProjectEvaluationResponse {

    private Long id;
    private Project project;
    private Evaluation evaluation;
    private Double score; // Puntaje de la evaluación para este proyecto
    private User users;

}
