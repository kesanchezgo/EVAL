package com.bezkoder.spring.security.postgresql.services;

import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.User;
import com.bezkoder.spring.security.postgresql.models.UserProjectAssignment;
import com.bezkoder.spring.security.postgresql.repository.ProjectRepository;
import com.bezkoder.spring.security.postgresql.repository.UserProjectAssignmentRepository;
import com.bezkoder.spring.security.postgresql.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
//import java.util.Collections;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserProjectAssignmentRepository userProjectAssignmentRepository;
    private final UserRepository userRepository; 

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserProjectAssignmentRepository userProjectAssignmentRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userProjectAssignmentRepository = userProjectAssignmentRepository;
        this.userRepository = userRepository;
    }

    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    /* public Page<Project> findAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    public Page<Project> findProjectsByNameContainingIgnoreCase(String search, Pageable pageable) {
        return projectRepository.findByNameContainingIgnoreCase(search, pageable);
    } */

    /* public Page<Project> getAllProjects(int page, int size, String sort, String order, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
        if (search != null && !search.isEmpty()) {
            //return projectRepository.findByNameContainingIgnoreCase(search, pageable);
            return projectRepository.findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(search,search, pageable);
        } else {
            return projectRepository.findAll(pageable);
        }
    } */

    public Page<Project> getAllProjects(int page, int size, String sort, String order, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
        if (search != null && !search.isEmpty()) {
            //return projectRepository.findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(search, search, pageable);
            return projectRepository.findByAuthorContainingIgnoreCase(search, pageable);
        } else {
            return projectRepository.findAll(pageable);
        }
    }



    public void assignProjectsRandomly(int numberOfProjects, List<Long> userIds) {
        List<Project> projects = projectRepository.findProjectsByCondition("N"); // Proyectos nuevos disponibles
    
        int projectsPerUser = numberOfProjects / userIds.size(); // Proyectos por usuario
    
        Random random = new Random();
    
        for (Long userId : userIds) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                for (int i = 0; i < projectsPerUser; i++) {
                    if (projects.isEmpty()) {
                        break; // Si ya no quedan proyectos disponibles, salir del bucle
                    }
    
                    int randomIndex = random.nextInt(projects.size()); // Índice aleatorio para seleccionar un proyecto
                    Project project = projects.remove(randomIndex); // Quitar el proyecto seleccionado de la lista
    
                    // Crear asignación de proyecto
                    UserProjectAssignment assignment = new UserProjectAssignment();
                    assignment.setUser(user); // Asignar el usuario obtenido
                    assignment.setProject(project);
                    userProjectAssignmentRepository.save(assignment);
    
                    // Actualizar el estado del proyecto a "A" (asignado)
                    project.setCondition("A");
                    projectRepository.save(project);
                }
            } else {
                // Manejar el caso donde el usuario no existe con ese ID
                // Puedes lanzar una excepción, loggear un mensaje de error, etc.
                System.err.println("User not found with ID: " + userId);
            }
        }
    }
    
}
