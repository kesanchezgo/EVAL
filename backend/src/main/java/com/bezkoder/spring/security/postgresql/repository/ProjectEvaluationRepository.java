package com.bezkoder.spring.security.postgresql.repository;
import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectEvaluationRepository extends JpaRepository<ProjectEvaluation, Long> {
    // Puedes agregar métodos adicionales según tus necesidades
    Optional<ProjectEvaluation> findByProjectAndEvaluation(Project project, Evaluation evaluation);
    //Page<ProjectEvaluation> findByEvaluationIdAndProject_NameContainingIgnoreCase(Long evaluationId, String name, Pageable pageable);
    
    Page<ProjectEvaluation> findByEvaluationIdAndProject_NameContainingIgnoreCase(Long evaluationId, String search, Pageable pageable);
    Page<ProjectEvaluation> findByEvaluationId(Long evaluationId, Pageable pageable);
    Page<ProjectEvaluation> findByProject_NameContainingIgnoreCase(String name, Pageable pageable);

    
    Page<ProjectEvaluation> findByProjectIdInAndEvaluationIdAndProject_NameContainingIgnoreCase(List<Long> projectIds, Long evaluationId, String search, Pageable pageable);
    Page<ProjectEvaluation> findByProjectIdInAndEvaluationId(List<Long> projectIds, Long evaluationId, Pageable pageable);
    Page<ProjectEvaluation> findByProjectIdInAndProject_NameContainingIgnoreCase(List<Long> projectIds, String name, Pageable pageable);
    Page<ProjectEvaluation> findByProjectIdIn(List<Long> projectIds, Pageable pageable);

    Page<ProjectEvaluation> findByProjectIdInAndEvaluationIdAndProjectConditionAndProject_NameContainingIgnoreCase(List<Long> projectIds,Long evaluationId,  String condition, String search, Pageable pageable);
    Page<ProjectEvaluation> findByProjectIdInAndEvaluationIdAndProjectCondition(List<Long> projectIds,Long evaluationId,  String condition, Pageable pageable);
    Page<ProjectEvaluation> findByProjectIdInAndProjectConditionAndProject_NameContainingIgnoreCase(List<Long> projectIds, String condition, String name, Pageable pageable);


    Page<ProjectEvaluation> findByEvaluationIdAndProjectConditionAndProject_NameContainingIgnoreCase(Long evaluationId,String condition, String search, Pageable pageable);
    Page<ProjectEvaluation> findByEvaluationIdAndProjectCondition(Long evaluationId,String condition, Pageable pageable);
    Page<ProjectEvaluation> findByProjectConditionAndProject_NameContainingIgnoreCase(String condition, String search, Pageable pageable);
    Page<ProjectEvaluation> findByProjectIdInAndProjectCondition(List<Long> projectIds, String condition, Pageable pageable);
    Page<ProjectEvaluation> findByProjectCondition(String condition, Pageable pageable);
}
