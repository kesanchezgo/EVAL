package com.bezkoder.spring.security.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.security.postgresql.models.SubcriterionScore;

@Repository
public interface SubcriterionScoreRepository extends JpaRepository<SubcriterionScore, Long> {
    // Aquí puedes agregar métodos de consulta adicionales si es necesario
    List<SubcriterionScore> findByProjectEvaluationProjectId(Long projectId);
}
