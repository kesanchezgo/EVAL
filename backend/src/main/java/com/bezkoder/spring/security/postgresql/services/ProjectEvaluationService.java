package com.bezkoder.spring.security.postgresql.services;

import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.models.User;
import com.bezkoder.spring.security.postgresql.payload.dto.ProjectEvaluationWithUsersDTO;
import com.bezkoder.spring.security.postgresql.repository.ProjectEvaluationRepository;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

//import jakarta.persistence.Query;
//import javax.persistence.Query;

@Service
public class ProjectEvaluationService {

    private final ProjectEvaluationRepository projectEvaluationRepository;

    @Autowired
    private EntityManager entityManager;
    

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
        return projectEvaluationRepository.findByProject_NameContainingIgnoreCaseOrProject_AuthorContainingIgnoreCase(search, search, pageable);
    }

    public Page<ProjectEvaluation> findAll(Pageable pageable) {
        return projectEvaluationRepository.findAll(pageable);
    }
    //
    public Page<ProjectEvaluation> findByProjectIdsAndEvaluationIdAndProjectNameContainingIgnoreCase(List<Long> projectIds, Long evaluationId, String search, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdInAndEvaluationIdAndProject_NameContainingIgnoreCase(projectIds,evaluationId, search, pageable);
    }

    public Page<ProjectEvaluation> findByProjectIdsAndEvaluationId(List<Long> projectIds, Long evaluationId, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdInAndEvaluationId(projectIds, evaluationId, pageable);
    }

    public Page<ProjectEvaluation> findByProjectIdsAndProjectNameContainingIgnoreCase(List<Long> projectIds, String search, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdInAndProject_NameContainingIgnoreCaseOrProject_AuthorContainingIgnoreCase(projectIds, search, search, pageable);
    }

    public Page<ProjectEvaluation> findByProjectIds(List<Long> projectIds, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdIn(projectIds, pageable);
    }

    //

    public Page<ProjectEvaluation> findByProjectIdsAndEvaluationIdAndConditionAndProjectNameContainingIgnoreCase(List<Long> projectIds, Long evaluationId, String condition,String search, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdInAndEvaluationIdAndProjectConditionAndProject_NameContainingIgnoreCase(projectIds,evaluationId, condition, search, pageable);
    }

    public Page<ProjectEvaluation> findByProjectIdsAndEvaluationIdAndCondition(List<Long> projectIds, Long evaluationId,String condition, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdInAndEvaluationIdAndProjectCondition(projectIds, evaluationId, condition, pageable);
    }

    public Page<ProjectEvaluation> findByProjectIdsAndConditionAndProjectNameContainingIgnoreCase(List<Long> projectIds, String condition, String search, Pageable pageable) {
        return projectEvaluationRepository.findByProjectIdInAndProjectConditionAndProject_NameContainingIgnoreCase(projectIds, condition, search, pageable);
    }


    public Page<ProjectEvaluation> findByEvaluationIdAndConditionAndProjectNameContainingIgnoreCase(Long evaluationId,String condition, String search, Pageable pageable){
        return projectEvaluationRepository.findByEvaluationIdAndProjectConditionAndProject_NameContainingIgnoreCase(evaluationId, condition, search, pageable);
    }
    public Page<ProjectEvaluation> findByEvaluationIdAndCondition(Long evaluationId,String condition, Pageable pageable){
        return projectEvaluationRepository.findByEvaluationIdAndProjectCondition(evaluationId, condition, pageable);
    }
    public Page<ProjectEvaluation> findByConditionAndProjectNameContainingIgnoreCase(String condition, String search, Pageable pageable){
        return projectEvaluationRepository.findByProjectConditionAndProject_NameContainingIgnoreCase(condition, search, pageable);
    }
    public Page<ProjectEvaluation> findByProjectIdsAndCondition(List<Long> projectIds, String condition, Pageable pageable){
        return projectEvaluationRepository.findByProjectIdInAndProjectCondition(projectIds, condition, pageable);
    }
    public Page<ProjectEvaluation> findByCondition(String condition, Pageable pageable){
        return projectEvaluationRepository.findByProjectCondition(condition, pageable);
    }


    public List<ProjectEvaluationWithUsersDTO> findProjectEvaluationsWithUsersByProjectId(Long projectId) {
        String query = "SELECT pe.id, pe.project, pe.evaluation, pe.score, upa.user "
                     + "FROM ProjectEvaluation pe "
                     + "JOIN pe.project p "
                     + "JOIN UserProjectAssignment upa ON p.id = upa.project.id "
                     + "WHERE p.id = :projectId";

        List<Object[]> results = entityManager.createQuery(query)
                                              .setParameter("projectId", projectId)
                                              .getResultList();

        List<ProjectEvaluationWithUsersDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            ProjectEvaluationWithUsersDTO dto = new ProjectEvaluationWithUsersDTO();
            dto.setId((Long) result[0]);
            dto.setProject((Project) result[1]);
            dto.setEvaluation((Evaluation) result[2]);
            dto.setScore((Double) result[3]);

            if (dto.getUsers() == null) {
                dto.setUsers(new HashSet<>());
            }
            dto.getUsers().add((User) result[4]);

            dtos.add(dto);
        }

        return dtos;
    }


    /* public Page<ProjectEvaluationWithUsersDTO> findProjectEvaluationsWithUsers(int page, int size, String sort, String order,
                                                                               Long projectId, Long evaluationId, String condition,
                                                                               String search, Long userId) {
        // Construir la consulta dinámica
        String query = "SELECT pe.id, pe.project, pe.evaluation, pe.score, upa.user "
                     + "FROM ProjectEvaluation pe "
                     + "JOIN pe.project p "
                     + "JOIN UserProjectAssignment upa ON p.id = upa.project.id ";
        
        // Agregar condiciones adicionales según los parámetros proporcionados
        if (projectId != null) {
            query += "WHERE p.id = :projectId";
        }
        if (evaluationId != null) {
            query += (projectId != null ? " AND " : " WHERE ") + "pe.evaluation.id = :evaluationId";
        }
        if (condition != null) {
            query += (projectId != null || evaluationId != null ? " AND " : " WHERE ") + "pe.condition = :condition";
        }
        if (search != null) {
            query += (projectId != null || evaluationId != null || condition != null ? " AND " : " WHERE ") + "p.name LIKE :search";
        }
        if (userId != null) {
            query += (projectId != null || evaluationId != null || condition != null || search != null ? " AND " : " WHERE ") + "upa.user.id = :userId";
        }

        // Construir la consulta SQL
        jakarta.persistence.Query jpaQuery = entityManager.createQuery(query);
        if (projectId != null) {
            jpaQuery.setParameter("projectId", projectId);
        }
        if (evaluationId != null) {
            jpaQuery.setParameter("evaluationId", evaluationId);
        }
        if (condition != null) {
            jpaQuery.setParameter("condition", condition);
        }
        if (search != null) {
            jpaQuery.setParameter("search", "%" + search + "%");
        }
        if (userId != null) {
            jpaQuery.setParameter("userId", userId);
        }

        // Aplicar paginación
        Sort.Direction direction = Sort.Direction.fromString(order);
        jpaQuery.setFirstResult(page * size).setMaxResults(size);
        jpaQuery.setHint("javax.persistence.loadgraph", "ProjectEvaluationWithUsersDTO");
        List<Object[]> results = jpaQuery.getResultList();

        // Mapear los resultados a DTO
        List<ProjectEvaluationWithUsersDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            ProjectEvaluationWithUsersDTO dto = new ProjectEvaluationWithUsersDTO();
            dto.setId((Long) result[0]);
            dto.setProject((Project) result[1]);
            dto.setEvaluation((Evaluation) result[2]);
            dto.setScore((Double) result[3]);

            if (dto.getUsers() == null) {
                dto.setUsers(new HashSet<>());
            }
            dto.getUsers().add((User) result[4]);

            dtos.add(dto);
        }

        // Construir una instancia de Page
        Page<ProjectEvaluationWithUsersDTO> pageResult = new PageImpl<>(dtos, PageRequest.of(page, size, direction, sort), dtos.size());

        return pageResult;
    } */

    public Page<ProjectEvaluationWithUsersDTO> findProjectEvaluationsWithUsers(int page, int size, String sort, String order,
                                                                               Long projectId, Long evaluationId, String condition,
                                                                               String search, Long userId) {
        // Construir la consulta dinámica
        String query = "SELECT pe.id, pe.project, pe.evaluation, pe.score, upa.user "
                     + "FROM ProjectEvaluation pe "
                     + "JOIN pe.project p "
                     + "JOIN UserProjectAssignment upa ON p.id = upa.project.id ";
        
        // Agregar condiciones adicionales según los parámetros proporcionados
        if (projectId != null) {
            query += "WHERE p.id = :projectId";
        }
        if (evaluationId != null) {
            query += (projectId != null ? " AND " : " WHERE ") + "pe.evaluation.id = :evaluationId";
        }
        if (condition != null) {
            query += (projectId != null || evaluationId != null ? " AND " : " WHERE ") + "pe.condition = :condition";
        }
        if (search != null) {
            query += (projectId != null || evaluationId != null || condition != null ? " AND " : " WHERE ") + "p.name LIKE :search";
        }
        if (userId != null) {
            query += (projectId != null || evaluationId != null || condition != null || search != null ? " AND " : " WHERE ") + "upa.user.id = :userId";
        }

        // Construir la consulta SQL
        jakarta.persistence.Query jpaQuery = entityManager.createQuery(query);
        if (projectId != null) {
            jpaQuery.setParameter("projectId", projectId);
        }
        if (evaluationId != null) {
            jpaQuery.setParameter("evaluationId", evaluationId);
        }
        if (condition != null) {
            jpaQuery.setParameter("condition", condition);
        }
        if (search != null) {
            jpaQuery.setParameter("search", "%" + search + "%");
        }
        if (userId != null) {
            jpaQuery.setParameter("userId", userId);
        }

        // Aplicar paginación
        Sort.Direction direction = Sort.Direction.fromString(order);
        jpaQuery.setFirstResult(page * size).setMaxResults(size);
        List<Object[]> results = jpaQuery.getResultList();

        // Mapear los resultados a DTO
        List<ProjectEvaluationWithUsersDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            ProjectEvaluationWithUsersDTO dto = new ProjectEvaluationWithUsersDTO();
            dto.setId((Long) result[0]);
            dto.setProject((Project) result[1]);
            dto.setEvaluation((Evaluation) result[2]);
            dto.setScore((Double) result[3]);

            if (dto.getUsers() == null) {
                dto.setUsers(new HashSet<>());
            }
            dto.getUsers().add((User) result[4]);

            dtos.add(dto);
        }

        // Contar el total de registros
        int totalResults = getTotalResults(query, projectId, evaluationId, condition, search, userId);

        // Construir una instancia de Page
        PageRequest pageRequest = PageRequest.of(page, size, direction, sort);
        Page<ProjectEvaluationWithUsersDTO> pageResult = new PageImpl<>(dtos, pageRequest, totalResults);

        return pageResult;
    }


     // Método auxiliar para contar el total de registros
     private int getTotalResults(String query, Long projectId, Long evaluationId, String condition,
     String search, Long userId) {
        // Construir la consulta SQL para contar el total de registros
        String countQuery = "SELECT COUNT(*) " + query.substring(query.indexOf("FROM"));
        jakarta.persistence.Query countJpaQuery = entityManager.createQuery(countQuery);
        if (projectId != null) {
            countJpaQuery.setParameter("projectId", projectId);
        }
        if (evaluationId != null) {
            countJpaQuery.setParameter("evaluationId", evaluationId);
        }
        if (condition != null) {
            countJpaQuery.setParameter("condition", condition);
        }
        if (search != null) {
            countJpaQuery.setParameter("search", "%" + search + "%");
        }
        if (userId != null) {
            countJpaQuery.setParameter("userId", userId);
        }

        // Obtener el total de registros
        Number totalResults = (Number) countJpaQuery.getSingleResult();

        return totalResults.intValue();
    }

    
}


