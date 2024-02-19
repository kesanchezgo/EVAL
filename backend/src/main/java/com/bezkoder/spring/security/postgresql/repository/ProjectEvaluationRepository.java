package com.bezkoder.spring.security.postgresql.repository;
import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectEvaluationRepository extends JpaRepository<ProjectEvaluation, Long> {
    // Puedes agregar métodos adicionales según tus necesidades
    Optional<ProjectEvaluation> findByProjectAndEvaluation(Project project, Evaluation evaluation);
}
