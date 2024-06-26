package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.payload.dto.ProjectEvaluationWithUsersDTO;
import com.bezkoder.spring.security.postgresql.repository.ProjectEvaluationRepository;
import com.bezkoder.spring.security.postgresql.repository.UserProjectAssignmentRepository;
import com.bezkoder.spring.security.postgresql.services.ProjectEvaluationService;
import com.bezkoder.spring.security.postgresql.services.ProjectService;
import com.bezkoder.spring.security.postgresql.services.UserProjectAssignmentService;
import com.bezkoder.spring.security.postgresql.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bezkoder.spring.security.postgresql.models.FilterType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200/", "http://vri.gestioninformacion.unsa.edu.pe/"})
@RequestMapping("/api/projectevaluation")
@RestController
public class ProjectEvaluationController {

    private final ProjectEvaluationService projectEvaluationService;
    private final ProjectService projectService;
    private final EvaluationService evaluationService;
    private final UserProjectAssignmentService userProjectAssignmentService;

    @Autowired
    public ProjectEvaluationController(ProjectEvaluationService projectEvaluationService,
                                       ProjectService projectService,
                                       EvaluationService evaluationService,
                                       UserProjectAssignmentService userProjectAssignmentService) {
        this.projectEvaluationService = projectEvaluationService;
        this.projectService = projectService;
        this.evaluationService = evaluationService;
        this.userProjectAssignmentService = userProjectAssignmentService;
    }

    @GetMapping("/project")
    public ResponseEntity<Project> getProjectByProjectAndEvaluation(@RequestParam("projectId") Long projectId,
                                                                    @RequestParam("evaluationId") Long evaluationId) {
        Optional<Project> project = projectService.findById(projectId);
        Optional<Evaluation> evaluation = evaluationService.findById(evaluationId);

        if (project.isPresent() && evaluation.isPresent()) {
            Optional<Project> result = projectEvaluationService.findProjectByProjectAndEvaluation(project.get(), evaluation.get());
            return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/project-evaluation")
    public ResponseEntity<ProjectEvaluation> getProjectEvaluationByProjectAndEvaluation(@RequestParam("projectId") Long projectId,
                                                                                         @RequestParam("evaluationId") Long evaluationId) {
   
        Optional<Project> project = projectService.findById(projectId);
        Optional<Evaluation> evaluation = evaluationService.findById(evaluationId);

        if (project.isPresent() && evaluation.isPresent()) {
            Optional<ProjectEvaluation> result = projectEvaluationService.findProjectEvaluationByProjectAndEvaluation(project.get(), evaluation.get());
            return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /* @GetMapping("/project-evaluations")
    public ResponseEntity<Page<ProjectEvaluation>> getProjectEvaluations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "project.externalId") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) Long evaluationId,
            @RequestParam(required = false) String search
    ) {
        Page<ProjectEvaluation> projectEvaluations;
        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort.Order sortOrder = new Sort.Order(direction, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
    
        if (evaluationId != null && search != null) {
            projectEvaluations = projectEvaluationService.findByEvaluationIdAndProjectNameContainingIgnoreCase(evaluationId, search, pageable);
        } else if (evaluationId != null) {
            projectEvaluations = projectEvaluationService.findByEvaluationId(evaluationId, pageable);
        } else if (search != null) {
            projectEvaluations = projectEvaluationService.findByProjectNameContainingIgnoreCase(search, pageable);
        } else {
            projectEvaluations = projectEvaluationService.findAll(pageable);
        }
    
        return new ResponseEntity<>(projectEvaluations, HttpStatus.OK);
    }
 */
    
    /* @GetMapping("/project-evaluations")
    public ResponseEntity<Page<ProjectEvaluation>> getProjectEvaluations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "project.externalId") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) Long evaluationId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long userId
    ) {
        Page<ProjectEvaluation> projectEvaluations;
        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort.Order sortOrder = new Sort.Order(direction, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));

        if (userId != null) {
            // Obtener los IDs de los proyectos asignados al usuario
            List<Long> projectIds = userProjectAssignmentService.findProjectIdsByUserId(userId);
            System.out.println("ids: "+projectIds);
            if (!projectIds.isEmpty()) {
                if (evaluationId != null && search != null) {
                    projectEvaluations = projectEvaluationService.findByProjectIdsAndEvaluationIdAndProjectNameContainingIgnoreCase(projectIds, evaluationId, search, pageable);
                } else if (evaluationId != null) {
                    projectEvaluations = projectEvaluationService.findByProjectIdsAndEvaluationId(projectIds, evaluationId, pageable);
                } else if (search != null) {
                    projectEvaluations = projectEvaluationService.findByProjectIdsAndProjectNameContainingIgnoreCase(projectIds, search, pageable);
                } else {
                    projectEvaluations = projectEvaluationService.findByProjectIds(projectIds, pageable);
                }
            } else {
                // Si el usuario no tiene proyectos asignados, retornar una lista vacía
                projectEvaluations = new PageImpl<>(Collections.emptyList());
            }
        } else {
            // Filtro normal cuando no se proporciona userId
            if (evaluationId != null && search != null) {
                projectEvaluations = projectEvaluationService.findByEvaluationIdAndProjectNameContainingIgnoreCase(evaluationId, search, pageable);
            } else if (evaluationId != null) {
                projectEvaluations = projectEvaluationService.findByEvaluationId(evaluationId, pageable);
            } else if (search != null) {
                projectEvaluations = projectEvaluationService.findByProjectNameContainingIgnoreCase(search, pageable);
            } else {
                projectEvaluations = projectEvaluationService.findAll(pageable);
            }
        }

        return new ResponseEntity<>(projectEvaluations, HttpStatus.OK);
    } */


    @GetMapping("/project-evaluations")
    public ResponseEntity<Page<ProjectEvaluation>> getProjectEvaluations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "project.externalId") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) Long evaluationId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String condition
    ) {
        if(condition==""){
            condition=null;
        }
        Page<ProjectEvaluation> projectEvaluations;
        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort.Order sortOrder = new Sort.Order(direction, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));

        FilterType filterType = getFilterType(evaluationId, search, condition);
        System.err.println("tessssss");
        switch (filterType) {
            case EVALUATION_ID_SEARCH_CONDITION: //
                System.err.println("t1");  
                projectEvaluations = userId != null ?
                        projectEvaluationService.findByProjectIdsAndEvaluationIdAndConditionAndProjectNameContainingIgnoreCase(
                                getProjectIds(userId), evaluationId, condition,search, pageable) :
                        projectEvaluationService.findByEvaluationIdAndConditionAndProjectNameContainingIgnoreCase(
                                evaluationId, condition, search, pageable);
                System.err.println("t1");                
                break;
            case EVALUATION_ID_SEARCH:
                System.err.println("t2"); 
                projectEvaluations = userId != null ?
                        projectEvaluationService.findByProjectIdsAndEvaluationIdAndProjectNameContainingIgnoreCase(
                                getProjectIds(userId), evaluationId, search, pageable) :
                        projectEvaluationService.findByEvaluationIdAndProjectNameContainingIgnoreCase(
                                evaluationId, search, pageable);
                System.err.println("t2");
                break;
            case EVALUATION_ID_CONDITION://
                System.err.println("t3");
                projectEvaluations = userId != null ?
                        projectEvaluationService.findByProjectIdsAndEvaluationIdAndCondition(
                                getProjectIds(userId), evaluationId, condition, pageable) :
                        projectEvaluationService.findByEvaluationIdAndCondition(
                                evaluationId, condition, pageable);
                System.err.println("t3");
                break;
            case SEARCH_CONDITION: //
                System.err.println("t8");
                projectEvaluations = userId != null ?
                        projectEvaluationService.findByProjectIdsAndConditionAndProjectNameContainingIgnoreCase(
                                getProjectIds(userId), condition, search, pageable) :
                        projectEvaluationService.findByConditionAndProjectNameContainingIgnoreCase(
                                condition, search, pageable);
                break;
            case EVALUATION_ID:
                System.err.println("t4");
                projectEvaluations = userId != null ?
                        projectEvaluationService.findByProjectIdsAndEvaluationId(
                                getProjectIds(userId), evaluationId, pageable) :
                        projectEvaluationService.findByEvaluationId(
                                evaluationId, pageable);
                System.err.println("t4");
                break;
            case SEARCH:
                System.err.println("t5");
                projectEvaluations = userId != null ?
                        projectEvaluationService.findByProjectIdsAndProjectNameContainingIgnoreCase(
                                getProjectIds(userId), search, pageable) :
                        projectEvaluationService.findByProjectNameContainingIgnoreCase(
                                search, pageable);
                break;
            case CONDITION://
                System.err.println("t6");
                projectEvaluations = userId != null ?
                        projectEvaluationService.findByProjectIdsAndCondition(
                                getProjectIds(userId), condition, pageable) :
                        projectEvaluationService.findByCondition(
                                condition, pageable);
                break;
            case NONE:
            default:
                System.err.println("t7");
                projectEvaluations = userId != null ?
                        projectEvaluationService.findByProjectIds(getProjectIds(userId), pageable) :
                        projectEvaluationService.findAll(pageable);
                break;
        }

        return new ResponseEntity<>(projectEvaluations, HttpStatus.OK);
    }

    private List<Long> getProjectIds(Long userId) {
        List<Long> projectIds = userProjectAssignmentService.findProjectIdsByUserId(userId);
        System.out.println("ids: " + projectIds);
        return projectIds;
    }

    /* private FilterType getFilterType(Long evaluationId, String search) {
        if (evaluationId != null && search != null) {
            return FilterType.EVALUATION_ID_SEARCH;
        } else if (evaluationId != null) {
            return FilterType.EVALUATION_ID;
        } else if (search != null) {
            return FilterType.SEARCH;
        } else {
            return FilterType.NONE;
        }
    } */

    private FilterType getFilterType(Long evaluationId, String search, String additionalParam) {
        if (evaluationId != null && search != null && additionalParam != null) {
            return FilterType.EVALUATION_ID_SEARCH_CONDITION;
        }   else if (evaluationId != null && search != null) {
            return FilterType.EVALUATION_ID_SEARCH;
        } else if (evaluationId != null && additionalParam != null) {
            return FilterType.EVALUATION_ID_CONDITION;
        } else if (search != null && additionalParam != null) {
            return FilterType.SEARCH_CONDITION;
        } else if (evaluationId != null) {
            return FilterType.EVALUATION_ID;
        } else if (search != null) {
            return FilterType.SEARCH;
        } else if (additionalParam != null) {
            return FilterType.CONDITION;
        } else {
            return FilterType.NONE;
        }
    }

    @GetMapping("/project-evaluations/{projectId}/with-users")
    public ResponseEntity<List<ProjectEvaluationWithUsersDTO>> getProjectEvaluationsWithUsers(
            @PathVariable Long projectId) {
        List<ProjectEvaluationWithUsersDTO> projectEvaluationsWithUsers = 
                projectEvaluationService.findProjectEvaluationsWithUsersByProjectId(projectId);

        return new ResponseEntity<>(projectEvaluationsWithUsers, HttpStatus.OK);
    }


    @GetMapping("/project-evaluations/with-users")
    public ResponseEntity<Page<ProjectEvaluationWithUsersDTO>> getProjectEvaluationsWithUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "project.externalId") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) Long evaluationId,
            @RequestParam(required = false) String condition,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long userId) {
        
        Page<ProjectEvaluationWithUsersDTO> projectEvaluationsWithUsers = 
                projectEvaluationService.findProjectEvaluationsWithUsers(page, size, sort, order, null, evaluationId, condition, search, userId);

        return new ResponseEntity<>(projectEvaluationsWithUsers, HttpStatus.OK);
    }

    

}
