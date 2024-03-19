package com.bezkoder.spring.security.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.security.postgresql.models.Subcriterion;

@Repository
public interface SubcriterionRepository extends JpaRepository<Subcriterion, Long> {
    // Aquí puedes agregar métodos de consulta adicionales si es necesario
   /*  @Query("SELECT s.criterion.weight FROM Subcriterion s WHERE s.id = :subcriterionId")
    Double findCriterionWeightBySubcriterionId(@Param("subcriterionId") Long subcriterionId); */
    /* @Query("SELECT sc.criterion.weight FROM Subcriterion sc JOIN sc.criterion c WHERE sc.id = :subcriterionId")
    Double findCriterionWeightBySubcriterionId(@Param("subcriterionId") Long subcriterionId); */
    

}

