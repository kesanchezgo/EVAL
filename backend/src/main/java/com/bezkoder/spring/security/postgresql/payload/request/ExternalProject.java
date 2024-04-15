package com.bezkoder.spring.security.postgresql.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Constructor sin argumentos
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalProject {
    private String id_proyecto;
    private String nomb_proy;
    private String principal;
    private String area_investigacion;
    private String area_ocde;
    private String est_proy;
    // Getters y setters
}

