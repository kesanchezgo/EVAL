package com.bezkoder.spring.security.postgresql.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.bezkoder.spring.security.postgresql.models.Criterion;
import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Subcriterion;
import com.bezkoder.spring.security.postgresql.payload.response.CriterionResponse;
import com.bezkoder.spring.security.postgresql.payload.response.SubcriterionResponse;
import com.bezkoder.spring.security.postgresql.repository.EvaluationRepository;
import org.springframework.data.domain.Sort;
import java.util.Comparator;

@CrossOrigin(origins = {"http://localhost:4200/", "http://vri.gestioninformacion.unsa.edu.pe/"})
@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationRepository evaluationRepository;

/*     @CrossOrigin(origins = "http://localhost:4200") */
    /* @GetMapping("/{evaluationId}/criteria")
    public ResponseEntity<?> getCriteriaByEvaluationId(@PathVariable Long evaluationId) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElse(null);

        if (evaluation == null) {
            return ResponseEntity.notFound().build();
        }
        List<Criterion> criteria = new ArrayList<>(evaluation.getCriterions());
        List<CriterionResponse> response = criteria.stream()
                .map(criterion -> new CriterionResponse(criterion.getId(), criterion.getName(),
                        criterion.getDescription(), criterion.getWeight(), getSubcriteriaForCriterion(criterion)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    } */

   /*  private List<SubcriterionResponse> getSubcriteriaForCriterion(Criterion criterion) {
        return criterion.getSubcriterias().stream()
                .map(subcriterion -> new SubcriterionResponse(subcriterion.getId(), subcriterion.getName(),
                        subcriterion.getDescription(), subcriterion.getWeight()))
                .collect(Collectors.toList());
    } */

    @GetMapping("/{evaluationId}/criteria")
    public ResponseEntity<?> getCriteriaByEvaluationId(@PathVariable Long evaluationId) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElse(null);

        if (evaluation == null) {
            return ResponseEntity.notFound().build();
        }
        List<Criterion> criteria = new ArrayList<>(evaluation.getCriterions());
        List<CriterionResponse> response = criteria.stream()
                .sorted(Comparator.comparing(Criterion::getId)) // Ordenar criterios por id ascendente
                .map(criterion -> new CriterionResponse(criterion.getId(), criterion.getName(),
                        criterion.getDescription(), criterion.getWeight(), getSubcriteriaForCriterion(criterion)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    private List<SubcriterionResponse> getSubcriteriaForCriterion(Criterion criterion) {
        return criterion.getSubcriterias().stream()
                .sorted(Comparator.comparing(Subcriterion::getId)) // Ordenar subcriterios por id ascendente
                .map(subcriterion -> new SubcriterionResponse(subcriterion.getId(), subcriterion.getName(),
                        subcriterion.getDescription(), subcriterion.getWeight(), subcriterion.getRange1(), subcriterion.getRange2()))
                .collect(Collectors.toList());
    }

   
}
