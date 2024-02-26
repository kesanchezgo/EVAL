package com.bezkoder.spring.security.postgresql.services;

import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.repository.ProjectEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;


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
}
