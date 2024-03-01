package com.bezkoder.spring.security.postgresql.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.models.Subcriterion;
import com.bezkoder.spring.security.postgresql.models.SubcriterionScore;
import com.bezkoder.spring.security.postgresql.payload.request.SubcriterionScoreRequest;
import com.bezkoder.spring.security.postgresql.services.SubcriterionScoreService;

@CrossOrigin(origins = {"http://localhost:4200/", "http://vri.gestioninformacion.unsa.edu.pe/"})
@RequestMapping("/api/subcriterion-scores")
@RestController
public class SubcriterionScoreController {

    private final SubcriterionScoreService subcriterionScoreService;

    @Autowired
    public SubcriterionScoreController(SubcriterionScoreService subcriterionScoreService) {
        this.subcriterionScoreService = subcriterionScoreService;
    }

    /* @PostMapping
    public ResponseEntity<SubcriterionScore> createSubcriterionScore(@RequestBody SubcriterionScore subcriterionScore) {
        SubcriterionScore createdSubcriterionScore = subcriterionScoreService.saveSubcriterionScore(subcriterionScore);
        return new ResponseEntity<>(createdSubcriterionScore, HttpStatus.CREATED);
    } */

    @PostMapping
    public ResponseEntity<SubcriterionScore> createSubcriterionScore(@RequestBody SubcriterionScoreRequest request) {
        SubcriterionScore subcriterionScore = new SubcriterionScore();
        ProjectEvaluation projectEvaluation = new ProjectEvaluation();
        projectEvaluation.setId(request.getProjectEvaluation());
        Subcriterion subcriterion = new Subcriterion();
        subcriterion.setId(request.getSubcriterion());
        subcriterionScore.setProjectEvaluation(projectEvaluation);
        subcriterionScore.setSubcriterion(subcriterion);
        subcriterionScore.setScore(request.getScore());
        
        SubcriterionScore createdSubcriterionScore = subcriterionScoreService.saveSubcriterionScore(subcriterionScore);
        return new ResponseEntity<>(createdSubcriterionScore, HttpStatus.CREATED);
    }

    /* @PostMapping("/subcriterionscores")
    public ResponseEntity<List<SubcriterionScore>> createSubcriterionScores(@RequestBody List<SubcriterionScore> subcriterionScores) {
        List<SubcriterionScore> createdSubcriterionScores = subcriterionScoreService.saveSubcriterionScores(subcriterionScores);
        return new ResponseEntity<>(createdSubcriterionScores, HttpStatus.CREATED);
    } */

    /* @PostMapping("/multiple")
    public ResponseEntity<List<SubcriterionScore>> createMultipleSubcriterionScores(@RequestBody List<SubcriterionScoreRequest> requests) {
        List<SubcriterionScore> createdSubcriterionScores = new ArrayList<>();
        
        for (SubcriterionScoreRequest request : requests) {
            SubcriterionScore subcriterionScore = new SubcriterionScore();
            ProjectEvaluation projectEvaluation = new ProjectEvaluation();
            projectEvaluation.setId(request.getProjectEvaluation());
            Subcriterion subcriterion = new Subcriterion();
            subcriterion.setId(request.getSubcriterion());
            subcriterionScore.setProjectEvaluation(projectEvaluation);
            subcriterionScore.setSubcriterion(subcriterion);
            subcriterionScore.setScore(request.getScore());
            
            SubcriterionScore createdSubcriterionScore = subcriterionScoreService.saveSubcriterionScore(subcriterionScore);
            createdSubcriterionScores.add(createdSubcriterionScore);
        }
        
        return new ResponseEntity<>(createdSubcriterionScores, HttpStatus.CREATED);
    } */

    @PostMapping("/multiple")
    public ResponseEntity<List<SubcriterionScore>> createMultipleSubcriterionScores(@RequestBody List<SubcriterionScoreRequest> requests) {
        List<SubcriterionScore> createdSubcriterionScores = new ArrayList<>();
        
        for (SubcriterionScoreRequest request : requests) {
            SubcriterionScore subcriterionScore = new SubcriterionScore();
            ProjectEvaluation projectEvaluation = new ProjectEvaluation();
            projectEvaluation.setId(request.getProjectEvaluation());
            Subcriterion subcriterion = new Subcriterion();
            subcriterion.setId(request.getSubcriterion());
            subcriterionScore.setProjectEvaluation(projectEvaluation);
            subcriterionScore.setSubcriterion(subcriterion);
            subcriterionScore.setScore(request.getScore());
            
            /* SubcriterionScore createdSubcriterionScore = subcriterionScoreService.saveSubcriterionScore(subcriterionScore); */
            createdSubcriterionScores.add(subcriterionScore);

            createdSubcriterionScores = subcriterionScoreService.saveMultipleSubcriterionScores(createdSubcriterionScores);
        }
        
        return new ResponseEntity<>(createdSubcriterionScores, HttpStatus.CREATED);
    }




    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getSubcriterionScoresByProjectId(@PathVariable Long projectId) {
        return subcriterionScoreService.getSubcriterionScoresByProjectId(projectId);
    }

    /* @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error interno. Detalles: " + e.getMessage());
    } */
}
