package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.payload.request.AssignProjectsRequest;
import com.bezkoder.spring.security.postgresql.repository.ProjectRepository;
import com.bezkoder.spring.security.postgresql.services.ProjectService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200/", "http://vri.gestioninformacion.unsa.edu.pe/"})
@RequestMapping("/api")
@RestController
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    /* @GetMapping("/projects")
    public Page<Project> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String search
    ) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort.Order sortOrder = new Sort.Order(direction, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));

        if (search != null && !search.isEmpty()) {
            return projectRepository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            return projectRepository.findAll(pageable);
        }
    } */
    //second
    /* @GetMapping("/projects")
    public Page<Project> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String search
    ) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort.Order sortOrder = new Sort.Order(direction, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));

        if (search != null && !search.isEmpty()) {
            return projectService.findProjectsByNameContainingIgnoreCase(search, pageable);
        } else {
            return projectService.findAllProjects(pageable);
        }
    } */

    @GetMapping("/projects")
    public Page<Project> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String search
    ) {
        return projectService.getAllProjects(page, size, sort, order, search);
    }

   

    @PostMapping("/assign")
    public ResponseEntity<String> assignProjects(@RequestBody AssignProjectsRequest request) {
        try {
            projectService.assignProjectsRandomly(request.getNumberOfProjects(), request.getUserIds());
            return ResponseEntity.status(HttpStatus.OK).body("Proyectos asignados correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al asignar proyectos: " + e.getMessage());
        }
    }

}

