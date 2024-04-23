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
import org.springframework.data.jpa.repository.Query;

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


    Page<ProjectEvaluation> findByProjectIdInAndEvaluationIdAndProjectConditionAndProject_NameContainingIgnoreCaseOrProject_AuthorContainingIgnoreCase(List<Long> projectIds,Long evaluationId,  String condition, String search, String search2, Pageable pageable);
    Page<ProjectEvaluation> findByProjectIdInAndProjectConditionAndProject_NameContainingIgnoreCaseOrProject_AuthorContainingIgnoreCase(List<Long> projectIds, String condition, String name, String name2, Pageable pageable);
    Page<ProjectEvaluation> findByEvaluationIdAndProjectConditionAndProject_NameContainingIgnoreCaseOrProject_AuthorContainingIgnoreCase(Long evaluationId,String condition, String search, String search2, Pageable pageable);
    Page<ProjectEvaluation> findByProjectConditionAndProject_NameContainingIgnoreCaseOrProject_AuthorContainingIgnoreCase(String condition, String search, String search2, Pageable pageable);

    /* @Query("SELECT pe FROM ProjectEvaluation pe WHERE pe.project.id IN :projectIds AND pe.evaluation.id = :evaluationId AND pe.project.condition = :condition AND (LOWER(pe.project.name) LIKE %:search% OR LOWER(pe.project.author) LIKE %:search2%)")
    Page<ProjectEvaluation> findByProjectIdInAndEvaluationIdAndProjectConditionAndProject_NameContainingIgnoreCaseOrProjectAuthorContainingIgnoreCase(List<Long> projectIds, Long evaluationId, String condition, String search, String search2, Pageable pageable);

    @Query("SELECT pe FROM ProjectEvaluation pe WHERE pe.project.id IN :projectIds AND pe.project.condition = :condition AND (LOWER(pe.project.name) LIKE %:search% OR LOWER(pe.project.author) LIKE %:search2%)")
    Page<ProjectEvaluation> findByProjectIdInAndProjectConditionAndProject_NameContainingIgnoreCaseOrProjectAuthorContainingIgnoreCase(List<Long> projectIds, String condition, String search, String search2, Pageable pageable);

    @Query("SELECT pe FROM ProjectEvaluation pe WHERE pe.evaluation.id = :evaluationId AND pe.project.condition = :condition AND (LOWER(pe.project.name) LIKE %:search% OR LOWER(pe.project.author) LIKE %:search2%)")
    Page<ProjectEvaluation> findByEvaluationIdAndProjectConditionAndProject_NameContainingIgnoreCaseOrProjectAuthorContainingIgnoreCase(Long evaluationId, String condition, String search, String search2, Pageable pageable);

    @Query("SELECT pe FROM ProjectEvaluation pe WHERE pe.project.condition = :condition AND (LOWER(pe.project.name) LIKE %:search% OR LOWER(pe.project.author) LIKE %:search2%)")
    Page<ProjectEvaluation> findByProjectConditionAndProject_NameContainingIgnoreCaseOrProjectAuthorContainingIgnoreCase(String condition, String search, String search2, Pageable pageable); */

    Page<ProjectEvaluation> findByProject_NameContainingIgnoreCaseOrProject_AuthorContainingIgnoreCase(String name, String name2, Pageable pageable);
    Page<ProjectEvaluation> findByProjectIdInAndProject_NameContainingIgnoreCaseOrProject_AuthorContainingIgnoreCase(List<Long> projectIds, String name, String name2, Pageable pageable);


    
}
