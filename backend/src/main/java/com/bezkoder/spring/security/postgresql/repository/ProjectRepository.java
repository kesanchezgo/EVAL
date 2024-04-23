package com.bezkoder.spring.security.postgresql.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bezkoder.spring.security.postgresql.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findBy(Pageable pageable);
    Page<Project> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Project> findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(String name, String author, Pageable pageable);
    Page<Project> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    Project findByExternalId(String externalId);
    List<Project> findProjectsByCondition(String condition);
    
}
