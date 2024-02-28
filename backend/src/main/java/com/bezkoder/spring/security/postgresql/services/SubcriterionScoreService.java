package com.bezkoder.spring.security.postgresql.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.models.Subcriterion;
import com.bezkoder.spring.security.postgresql.models.SubcriterionScore;
import com.bezkoder.spring.security.postgresql.repository.ProjectEvaluationRepository;
import com.bezkoder.spring.security.postgresql.repository.SubcriterionScoreRepository;
import com.bezkoder.spring.security.postgresql.repository.SubcriterionRepository;
@Service
public class SubcriterionScoreService {

    private final SubcriterionScoreRepository subcriterionScoreRepository;
    private final ProjectEvaluationRepository projectEvaluationRepository;
    private final SubcriterionRepository subcriterionRepository;

    @Autowired
    public SubcriterionScoreService(SubcriterionScoreRepository subcriterionScoreRepository,
                                     ProjectEvaluationRepository projectEvaluationRepository,
                                     SubcriterionRepository subcriterionRepository) {
        this.subcriterionScoreRepository = subcriterionScoreRepository;
        this.projectEvaluationRepository = projectEvaluationRepository;
        this.subcriterionRepository = subcriterionRepository;
    }

    /* public SubcriterionScore saveSubcriterionScore(SubcriterionScore subcriterionScore) {
        
        ProjectEvaluation projectEvaluation = projectEvaluationRepository.findById(subcriterionScore.getProjectEvaluation().getId()).orElse(null);
        Subcriterion subcriterion = subcriterionRepository.findById(subcriterionScore.getSubcriterion().getId()).orElse(null);

        if (projectEvaluation != null && subcriterion != null) {
            subcriterionScore.setProjectEvaluation(projectEvaluation);
            subcriterionScore.setSubcriterion(subcriterion);
            return subcriterionScoreRepository.save(subcriterionScore);
        } else {
           
            return null;
            

        }
    } */

    public SubcriterionScore saveSubcriterionScore(SubcriterionScore subcriterionScore) {
        // Cargar projectEvaluation y subcriterion
        ProjectEvaluation projectEvaluation = projectEvaluationRepository.findById(subcriterionScore.getProjectEvaluation().getId()).orElse(null);
        Subcriterion subcriterion = subcriterionRepository.findById(subcriterionScore.getSubcriterion().getId()).orElse(null);
    
        if (projectEvaluation != null && subcriterion != null) {
            subcriterionScore.setProjectEvaluation(projectEvaluation);
            subcriterionScore.setSubcriterion(subcriterion);
            return subcriterionScoreRepository.save(subcriterionScore);
        } else {
            // Manejar la lógica de error si no se encuentran projectEvaluation o subcriterion
            return null;
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró projectEvaluation o subcriterion con los IDs proporcionados");
        }
    }

    /* public List<SubcriterionScore> saveSubcriterionScores(List<SubcriterionScore> subcriterionScores) {
        List<SubcriterionScore> createdSubcriterionScores = new ArrayList<>();

        for (SubcriterionScore subcriterionScore : subcriterionScores) {
            // Cargar projectEvaluation y subcriterion
            ProjectEvaluation projectEvaluation = projectEvaluationRepository.findById(subcriterionScore.getProjectEvaluation().getId()).orElse(null);
            Subcriterion subcriterion = subcriterionRepository.findById(subcriterionScore.getSubcriterion().getId()).orElse(null);

            if (projectEvaluation != null && subcriterion != null) {
                subcriterionScore.setProjectEvaluation(projectEvaluation);
                subcriterionScore.setSubcriterion(subcriterion);
                createdSubcriterionScores.add(subcriterionScoreRepository.save(subcriterionScore));
            }
            // Puedes manejar los casos donde no se encuentren projectEvaluation o subcriterion
        }

        return createdSubcriterionScores;
    } */

    public List<SubcriterionScore> saveMultipleSubcriterionScores(List<SubcriterionScore> subcriterionScores) {
        List<SubcriterionScore> createdSubcriterionScores = new ArrayList<>();
        
        for (SubcriterionScore subcriterionScore : subcriterionScores) {
            ProjectEvaluation projectEvaluation = projectEvaluationRepository.findById(subcriterionScore.getProjectEvaluation().getId()).orElse(null);
            Subcriterion subcriterion = subcriterionRepository.findById(subcriterionScore.getSubcriterion().getId()).orElse(null);
    
            if (projectEvaluation != null && subcriterion != null) {
                subcriterionScore.setProjectEvaluation(projectEvaluation);
                subcriterionScore.setSubcriterion(subcriterion);
                createdSubcriterionScores.add(subcriterionScoreRepository.save(subcriterionScore));
            } else {
                // Manejar la lógica de error si no se encuentran projectEvaluation o subcriterion
            }
        }
        
        return createdSubcriterionScores;
    }
    

    

    public ResponseEntity<?> getSubcriterionScoresByProjectId(Long projectId) {
        return ResponseEntity.ok(subcriterionScoreRepository.findByProjectEvaluationProjectId(projectId));
    }
}

