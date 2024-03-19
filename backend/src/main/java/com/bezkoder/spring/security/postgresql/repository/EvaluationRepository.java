package com.bezkoder.spring.security.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.security.postgresql.models.Criterion;
import com.bezkoder.spring.security.postgresql.models.Evaluation;
import java.util.List;
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    // Aquí puedes agregar métodos de consulta personalizados si los necesitas
    //List<Criterion> findCriteriaByEvaluationIdOrderByCriteriaIdAsc(Long evaluationId);
    //List<Criterion> findCriterionByCriteriaIdOrderByCriterionIdAsc(Long criteriaId);
           
}
