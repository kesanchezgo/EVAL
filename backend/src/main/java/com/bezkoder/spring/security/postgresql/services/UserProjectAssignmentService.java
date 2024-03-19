package com.bezkoder.spring.security.postgresql.services;

import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.models.User;
import com.bezkoder.spring.security.postgresql.models.UserProjectAssignment;
import com.bezkoder.spring.security.postgresql.repository.ProjectEvaluationRepository;
import com.bezkoder.spring.security.postgresql.repository.UserProjectAssignmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class UserProjectAssignmentService {

    private final UserProjectAssignmentRepository userProjectAssignmentRepository;

    @Autowired
    public UserProjectAssignmentService(UserProjectAssignmentRepository userProjectAssignmentRepository) {
        this.userProjectAssignmentRepository = userProjectAssignmentRepository;
    }

    public Optional<Project> findProjectByUser(User user) {
        return userProjectAssignmentRepository.findByUser(user)
                .map(UserProjectAssignment::getProject);
    }

    public Optional<UserProjectAssignment> findUserProjectAssignmentByUser(User user) {
        return userProjectAssignmentRepository.findByUser(user);
    }

    ////
    public Page<UserProjectAssignment> findByUserIdAndProjectNameContainingIgnoreCase(Long userId, String search, Pageable pageable) {
        return userProjectAssignmentRepository.findByUserIdAndProject_NameContainingIgnoreCase(userId, search, pageable);
    }

    public Page<UserProjectAssignment> findByUserId(Long userId, Pageable pageable) {
        return userProjectAssignmentRepository.findByUserId(userId, pageable);
    }

    public Page<UserProjectAssignment> findByProjectNameContainingIgnoreCase(String search, Pageable pageable) {
        return userProjectAssignmentRepository.findByProject_NameContainingIgnoreCase(search, pageable);
    }

    public Page<UserProjectAssignment> findAll(Pageable pageable) {
        return userProjectAssignmentRepository.findAll(pageable);
    }

    public List<Long>findProjectIdsByUserId(Long userId){
        return userProjectAssignmentRepository.findProjectIdByUserId(userId);
    }
}
