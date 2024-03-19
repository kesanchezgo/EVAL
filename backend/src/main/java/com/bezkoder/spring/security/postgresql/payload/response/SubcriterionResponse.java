package com.bezkoder.spring.security.postgresql.payload.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // Constructor sin argumentos
public class SubcriterionResponse {

    private Long id;
    private String name;
    private String description;
    private double weight;
    private int range1;
    private int range2;
    // Getters and Setters
}
