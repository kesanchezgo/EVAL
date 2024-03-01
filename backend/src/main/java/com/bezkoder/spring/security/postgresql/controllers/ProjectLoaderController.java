package com.bezkoder.spring.security.postgresql.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bezkoder.spring.security.postgresql.services.ProjectLoaderService;
import io.jsonwebtoken.io.IOException;


@CrossOrigin(origins = {"http://localhost:4200/", "http://vri.gestioninformacion.unsa.edu.pe/}"})
@RequestMapping("/api")
@RestController
public class ProjectLoaderController {

    @Autowired
    private ProjectLoaderService projectLoaderService;

    @PostMapping("/loadProjects")
    public ResponseEntity<String> loadProjects(@RequestBody Long evaluationId) {
        try {
            projectLoaderService.loadProjectsFromSismo(evaluationId);
            return ResponseEntity.ok().body("Proyectos cargados exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar los proyectos.");
        }
    }
}
