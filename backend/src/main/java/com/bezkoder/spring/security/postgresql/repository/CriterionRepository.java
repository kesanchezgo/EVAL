package com.bezkoder.spring.security.postgresql.repository;

import com.bezkoder.spring.security.postgresql.models.Criterion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CriterionRepository extends JpaRepository<Criterion, Long> {

    @Query("SELECT c.weight FROM Criterion c JOIN c.subcriterias sc WHERE sc.id = :subcriterionId")
    Double findWeightBySubcriterionId(@Param("subcriterionId") Long subcriterionId);

    @Query("SELECT c FROM Criterion c JOIN c.subcriterias sc WHERE sc.id = :subcriterionId")
    Criterion findBySubcriterionId(@Param("subcriterionId") Long subcriterionId);
}
