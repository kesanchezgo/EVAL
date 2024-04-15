package com.bezkoder.spring.security.postgresql.repository;
import com.bezkoder.spring.security.postgresql.models.LineResearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineResearchRepository extends JpaRepository<LineResearch, Long> {
    // Puedes agregar métodos personalizados de consulta aquí si es necesario
}
