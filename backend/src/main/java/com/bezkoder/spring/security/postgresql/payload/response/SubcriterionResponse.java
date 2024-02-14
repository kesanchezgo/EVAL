package com.bezkoder.spring.security.postgresql.payload.response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Constructor sin argumentos
public class SubcriterionResponse {

    private Long id;
    private String name;
    private String description;
    private double weight;

    public SubcriterionResponse(Long id, String name, String description, double weight) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    // Getters and Setters
}
