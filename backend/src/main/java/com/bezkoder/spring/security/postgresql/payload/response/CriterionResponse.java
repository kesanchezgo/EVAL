package com.bezkoder.spring.security.postgresql.payload.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Constructor sin argumentos
public class CriterionResponse {

    private Long id;
    private String name;
    private String description;
    private double weight;
    private List<SubcriterionResponse> subcriteria;

    public CriterionResponse(Long id, String name, String description, double weight,
            List<SubcriterionResponse> subcriteria) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.subcriteria = subcriteria;
    }

    // Getters and Setters
}
