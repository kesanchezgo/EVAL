package com.bezkoder.spring.security.postgresql.payload.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Constructor sin argumentos
public class AssignProjectsRequest {
    private int numberOfProjects;
    private List<Long> userIds;

    // Getters y setters
    // Constructor
}
