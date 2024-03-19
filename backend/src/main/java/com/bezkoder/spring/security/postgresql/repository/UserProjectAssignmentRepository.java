package com.bezkoder.spring.security.postgresql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.models.User;
import com.bezkoder.spring.security.postgresql.models.UserProjectAssignment;

import org.springframework.data.domain.Pageable;

@Repository
public interface UserProjectAssignmentRepository extends JpaRepository<UserProjectAssignment, Long> {
   
    // Puedes agregar métodos adicionales según tus necesidades
    Optional<UserProjectAssignment> findByUser(User user);
   
    Page<UserProjectAssignment> findByUserIdAndProject_NameContainingIgnoreCase(Long userId, String search, Pageable pageable);

    Page<UserProjectAssignment> findByUserId(Long UserId, Pageable pageable);
    Page<UserProjectAssignment> findByProject_NameContainingIgnoreCase(String name, Pageable pageable);
    //List<Long> findProjectIdsByUserId(Long UserId);

    @Query("SELECT upa.project.id FROM UserProjectAssignment upa WHERE upa.user.id = :userId")
    List<Long> findProjectIdByUserId(Long userId);
}
