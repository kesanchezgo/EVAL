package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.repository.ProjectEvaluationRepository;
import com.bezkoder.spring.security.postgresql.services.ProjectEvaluationService;
import com.bezkoder.spring.security.postgresql.services.ProjectService;
import com.bezkoder.spring.security.postgresql.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/projectevaluation")
@RestController
public class ProjectEvaluationController {

    private final ProjectEvaluationService projectEvaluationService;
    private final ProjectService projectService;
    private final EvaluationService evaluationService;

    @Autowired
    public ProjectEvaluationController(ProjectEvaluationService projectEvaluationService,
                                       ProjectService projectService,
                                       EvaluationService evaluationService) {
        this.projectEvaluationService = projectEvaluationService;
        this.projectService = projectService;
        this.evaluationService = evaluationService;
    }

    @GetMapping("/project")
    public ResponseEntity<Project> getProjectByProjectAndEvaluation(@RequestParam("projectId") Long projectId,
                                                                    @RequestParam("evaluationId") Long evaluationId) {
        Optional<Project> project = projectService.findById(projectId);
        Optional<Evaluation> evaluation = evaluationService.findById(evaluationId);

        if (project.isPresent() && evaluation.isPresent()) {
            Optional<Project> result = projectEvaluationService.findProjectByProjectAndEvaluation(project.get(), evaluation.get());
            return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/project-evaluation")
    public ResponseEntity<ProjectEvaluation> getProjectEvaluationByProjectAndEvaluation(@RequestParam("projectId") Long projectId,
                                                                                         @RequestParam("evaluationId") Long evaluationId) {
   
        Optional<Project> project = projectService.findById(projectId);
        Optional<Evaluation> evaluation = evaluationService.findById(evaluationId);

        if (project.isPresent() && evaluation.isPresent()) {
            Optional<ProjectEvaluation> result = projectEvaluationService.findProjectEvaluationByProjectAndEvaluation(project.get(), evaluation.get());
            return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /* @GetMapping("/projects-evaluation")
    public Page<ProjectEvaluation> getAllProjectsByEvaluation(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long evaluationId,
            @Autowired ProjectEvaluationService projectEvaluationService // Inyectamos el servicio
    ) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort.Order sortOrder = new Sort.Order(direction, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));

        if (evaluationId != null) {
            if (search != null && !search.isEmpty()) {
                return projectEvaluationService.findByEvaluationIdAndProjectNameContainingIgnoreCase(evaluationId, search, pageable);
            } else {
                return projectEvaluationService.findByEvaluationId(evaluationId, pageable);
            }
        } else {
            if (search != null && !search.isEmpty()) {
                return projectEvaluationService.findByProjectNameContainingIgnoreCase(search, pageable);
            } else {
                return projectEvaluationService.findAll(pageable);
            }
        }
    } */

    /* @GetMapping("/project-evaluations")
    public ResponseEntity<Page<ProjectEvaluation>> getProjectEvaluations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) Long evaluationId,
            @RequestParam(required = false) String projectName
    ) {
        Page<ProjectEvaluation> projectEvaluations;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));

        if (evaluationId != null && projectName != null) {
            projectEvaluations = projectEvaluationService.findByEvaluationIdAndProjectNameContainingIgnoreCase(evaluationId, projectName, pageable);
        } else if (evaluationId != null) {
            projectEvaluations = projectEvaluationService.findByEvaluationId(evaluationId, pageable);
        } else if (projectName != null) {
            projectEvaluations = projectEvaluationService.findByProjectNameContainingIgnoreCase(projectName, pageable);
        } else {
            projectEvaluations = projectEvaluationService.findAll(pageable);
        }

        return new ResponseEntity<>(projectEvaluations, HttpStatus.OK);
    } */

    @GetMapping("/project-evaluations")
    public ResponseEntity<Page<ProjectEvaluation>> getProjectEvaluations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "project.externalId") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) Long evaluationId,
            @RequestParam(required = false) String projectName
    ) {
        Page<ProjectEvaluation> projectEvaluations;
        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort.Order sortOrder = new Sort.Order(direction, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
    
        if (evaluationId != null && projectName != null) {
            projectEvaluations = projectEvaluationService.findByEvaluationIdAndProjectNameContainingIgnoreCase(evaluationId, projectName, pageable);
        } else if (evaluationId != null) {
            projectEvaluations = projectEvaluationService.findByEvaluationId(evaluationId, pageable);
        } else if (projectName != null) {
            projectEvaluations = projectEvaluationService.findByProjectNameContainingIgnoreCase(projectName, pageable);
        } else {
            projectEvaluations = projectEvaluationService.findAll(pageable);
        }
    
        return new ResponseEntity<>(projectEvaluations, HttpStatus.OK);
    }


}
