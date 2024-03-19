package com.bezkoder.spring.security.postgresql.services;

import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.repository.ProjectEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectEvaluationService {

    private final ProjectEvaluationRepository projectEvaluationRepository;

    @Autowired
    public ProjectEvaluationService(ProjectEvaluationRepository projectEvaluationRepository) {
        this.projectEvaluationRepository = projectEvaluationRepository;
    }

    public Optional<Project> findProjectByProjectAndEvaluation(Project project, Evaluation evaluation) {
        return projectEvaluationRepository.findByProjectAndEvaluation(project, evaluation)
                .map(ProjectEvaluation::getProject);
    }

    public Optional<ProjectEvaluation> findProjectEvaluationByProjectAndEvaluation(Project project, Evaluation evaluation) {
        return projectEvaluationRepository.findByProjectAndEvaluation(project, evaluation);
    }

    ////
    public Page<ProjectEvaluation> findByEvaluationIdAndProjectNameContainingIgnoreCase(Long evaluationId, String search, Pageable pageable) {
        return projectEvaluationRepository.findByEvaluationIdAndProject_NameContainingIgnoreCase(evaluationId, search, pageable);
    }

    public Page<ProjectEvaluation> findByEvaluationId(Long evaluationId, Pageable pageable) {
        return projectEvaluationRepository.findByEvaluationId(evaluationId, pageable);
    }

    public Page<ProjectEvaluation> findByProjectNameContainingIgnoreCase(String search, Pageable pageable) {
        return projectEvaluationRepository.findByProject_NameContainingIgnoreCase(search, pageable);
    }

    public Page<ProjectEvaluation> findAll(Pageable pageable) {
        return projectEvaluationRepository.findAll(pageable);
    }
    //
    public Page<ProjectEvaluation> findByProjectIdsAndEvaluationIdAndProjectNameContainingIgnoreCase(List<Long> projectIds, Long evaluationId, String search, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdInAndEvaluationIdAndProject_NameContainingIgnoreCase(projectIds,evaluationId, search, pageable);
    }

    public Page<ProjectEvaluation> findByProjectIdsAndEvaluationId(List<Long> projectIds, Long evaluationId, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdInAndEvaluationId(projectIds, evaluationId, pageable);
    }

    public Page<ProjectEvaluation> findByProjectIdsAndProjectNameContainingIgnoreCase(List<Long> projectIds, String search, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdInAndProject_NameContainingIgnoreCase(projectIds, search, pageable);
    }

    public Page<ProjectEvaluation> findByProjectIds(List<Long> projectIds, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdIn(projectIds, pageable);
    }

    //

    public Page<ProjectEvaluation> findByProjectIdsAndEvaluationIdAndConditionAndProjectNameContainingIgnoreCase(List<Long> projectIds, Long evaluationId, String condition,String search, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdInAndEvaluationIdAndProjectConditionAndProject_NameContainingIgnoreCase(projectIds,evaluationId, condition, search, pageable);
    }

    public Page<ProjectEvaluation> findByProjectIdsAndEvaluationIdAndCondition(List<Long> projectIds, Long evaluationId,String condition, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdInAndEvaluationIdAndProjectCondition(projectIds, evaluationId, condition, pageable);
    }

    public Page<ProjectEvaluation> findByProjectIdsAndConditionAndProjectNameContainingIgnoreCase(List<Long> projectIds, String condition, String search, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdInAndProjectConditionAndProject_NameContainingIgnoreCase(projectIds, condition, search, pageable);
    }


    public Page<ProjectEvaluation> findByEvaluationIdAndConditionAndProjectNameContainingIgnoreCase(Long evaluationId,String condition, String search, Pageable pageable){
        return projectEvaluationRepository.findByEvaluationIdAndProjectConditionAndProject_NameContainingIgnoreCase(evaluationId, condition, search, pageable);
    }
    public Page<ProjectEvaluation> findByEvaluationIdAndCondition(Long evaluationId,String condition, Pageable pageable){
        return projectEvaluationRepository.findByEvaluationIdAndProjectCondition(evaluationId, condition, pageable);
    }
    public Page<ProjectEvaluation> findByConditionAndProjectNameContainingIgnoreCase(String condition, String search, Pageable pageable){
        return projectEvaluationRepository.findByProjectConditionAndProject_NameContainingIgnoreCase(condition, search, pageable);
    }
    public Page<ProjectEvaluation> findByProjectIdsAndCondition(List<Long> projectIds, String condition, Pageable pageable){
        return projectEvaluationRepository.findByProjectIdInAndProjectCondition(projectIds, condition, pageable);
    }
    public Page<ProjectEvaluation> findByCondition(String condition, Pageable pageable){
        return projectEvaluationRepository.findByProjectCondition(condition, pageable);
    }

    
}


