package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.services.ProjectEvaluationService;
import com.bezkoder.spring.security.postgresql.services.ProjectService;
import com.bezkoder.spring.security.postgresql.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
