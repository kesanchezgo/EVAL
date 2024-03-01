package com.bezkoder.spring.security.postgresql.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bezkoder.spring.security.postgresql.models.Criterion;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.models.Subcriterion;
import com.bezkoder.spring.security.postgresql.models.SubcriterionScore;
import com.bezkoder.spring.security.postgresql.repository.CriterionRepository;
import com.bezkoder.spring.security.postgresql.repository.ProjectEvaluationRepository;
import com.bezkoder.spring.security.postgresql.repository.SubcriterionScoreRepository;
import com.bezkoder.spring.security.postgresql.repository.SubcriterionRepository;
@Service
public class SubcriterionScoreService {

    private final SubcriterionScoreRepository subcriterionScoreRepository;
    private final ProjectEvaluationRepository projectEvaluationRepository;
    private final SubcriterionRepository subcriterionRepository;
    private final CriterionRepository criterionRepository;

    @Autowired
    public SubcriterionScoreService(SubcriterionScoreRepository subcriterionScoreRepository,
                                     ProjectEvaluationRepository projectEvaluationRepository,
                                     SubcriterionRepository subcriterionRepository,
                                     CriterionRepository criterionRepository) {
        this.subcriterionScoreRepository = subcriterionScoreRepository;
        this.projectEvaluationRepository = projectEvaluationRepository;
        this.subcriterionRepository = subcriterionRepository;
        this.criterionRepository = criterionRepository;
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
    
    public List<SubcriterionScore> saveMultipleSubcriterionScores(List<SubcriterionScore> subcriterionScores) {
        List<SubcriterionScore> createdSubcriterionScores = new ArrayList<>();
        
        // Map para almacenar los pesos de los criterios asociados a los subcriterios
        Map<Criterion, Double> criterionWeightsMap = new HashMap<>();

        //Todos los subcriterios pertenecen a sun solo proyecto
        SubcriterionScore firstSubcriterionScore = subcriterionScores.get(0);
        ProjectEvaluation projectEvaluation = projectEvaluationRepository.findById(firstSubcriterionScore.getProjectEvaluation().getId()).orElse(null);
       
        for (SubcriterionScore subcriterionScore : subcriterionScores) {
            
            Subcriterion subcriterion = subcriterionRepository.findById(subcriterionScore.getSubcriterion().getId()).orElse(null);

            if (projectEvaluation != null && subcriterion != null) {
                subcriterionScore.setProjectEvaluation(projectEvaluation);
                subcriterionScore.setSubcriterion(subcriterion);
                createdSubcriterionScores.add(subcriterionScoreRepository.save(subcriterionScore));
                
                // Actualizar el mapa de pesos de criterios
                Criterion criterion = criterionRepository.findBySubcriterionId(subcriterion.getId());
                double score = subcriterionScore.getScore();
                criterionWeightsMap.put(criterion, score);
            } else {
                // Manejar la lógica de error si no se encuentran projectEvaluation o subcriterion
            }
        }
        
        // Calcular el puntaje total de la evaluación
        double totalScore = 0.0;
        //System.out.println("entre");
        for (Map.Entry<Criterion, Double> entry : criterionWeightsMap.entrySet()) {
            Criterion criterion = entry.getKey();
            Double weight = entry.getValue();
            totalScore += weight * (criterion.getWeight()/100); // Multiplicar el peso del criterio por el peso del subcriterio
            System.out.println("peso: "+ weight);
            System.out.println("getWeight: "+  criterion.getWeight());
        }
        
        // Actualizar el puntaje total en el objeto ProjectEvaluation
        projectEvaluation.setScore(totalScore);
        projectEvaluation.getProject().setCondition("R");
        projectEvaluationRepository.save(projectEvaluation);
        
        return createdSubcriterionScores;
    }



    public ResponseEntity<?> getSubcriterionScoresByProjectId(Long projectId) {
        return ResponseEntity.ok(subcriterionScoreRepository.findByProjectEvaluationProjectId(projectId));
    }
}

