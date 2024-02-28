package com.bezkoder.spring.security.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.security.postgresql.models.Subcriterion;

@Repository
public interface SubcriterionRepository extends JpaRepository<Subcriterion, Long> {
    // Aquí puedes agregar métodos de consulta adicionales si es necesario
}

