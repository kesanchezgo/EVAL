package com.bezkoder.spring.security.postgresql.payload.dto;

import java.util.Set;

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
public class ProjectEvaluationWithUsersDTO {
    private Long id;
    private Project project;
    private Evaluation evaluation;
    private Double score;
    private Set<User> users;

    // Getters y Setters
}

