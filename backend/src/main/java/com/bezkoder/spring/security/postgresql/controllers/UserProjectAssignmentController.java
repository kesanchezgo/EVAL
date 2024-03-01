package com.bezkoder.spring.security.postgresql.controllers;
import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.models.User;
import com.bezkoder.spring.security.postgresql.models.UserProjectAssignment;
import com.bezkoder.spring.security.postgresql.repository.ProjectEvaluationRepository;
import com.bezkoder.spring.security.postgresql.repository.UserProjectAssignmentRepository;
import com.bezkoder.spring.security.postgresql.services.ProjectEvaluationService;
import com.bezkoder.spring.security.postgresql.services.ProjectService;
import com.bezkoder.spring.security.postgresql.services.UserProjectAssignmentService;
import com.bezkoder.spring.security.postgresql.services.UserService;
import com.bezkoder.spring.security.postgresql.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200/", "http://vri.gestioninformacion.unsa.edu.pe/"})
@RequestMapping("/api/projectassignment")
@RestController
public class UserProjectAssignmentController {

    private final UserProjectAssignmentService userProjectAssignmentService;
    private final UserService userService;

    @Autowired
    public UserProjectAssignmentController(UserProjectAssignmentService userProjectAssignmentService,
                                       UserService userService) {
        this.userProjectAssignmentService = userProjectAssignmentService;
        this.userService = userService;
    }

    //agregar el filtro de proyecto para tener unico resultado, copiar ProjectEvaluation
    /* @GetMapping("/project-assigned")
    public ResponseEntity<UserProjectAssignment> getProjectEvaluationByProjectAndEvaluation(@RequestParam("userId") Long userId) {
   
        Optional<User> user = userService.findById(userId);
        

        if (user.isPresent()) {
            Optional<UserProjectAssignment> result = userProjectAssignmentService.findUserProjectAssignmentByUser(user.get());
            return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    } */

    @GetMapping("/project-assigneds")
    public ResponseEntity<Page<UserProjectAssignment>> getProjectAssigneds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "project.externalId") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String search
    ) {
        Page<UserProjectAssignment> projectAssigneds;
        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort.Order sortOrder = new Sort.Order(direction, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
    
        if (userId != null && search != null) {
            projectAssigneds = userProjectAssignmentService.findByUserIdAndProjectNameContainingIgnoreCase(userId, search, pageable);
        } else if (userId != null) {
            projectAssigneds = userProjectAssignmentService.findByUserId(userId, pageable);
        } else if (search != null) {
            projectAssigneds = userProjectAssignmentService.findByProjectNameContainingIgnoreCase(search, pageable);
        } else {
            projectAssigneds = userProjectAssignmentService.findAll(pageable);
        }
    
        return new ResponseEntity<>(projectAssigneds, HttpStatus.OK);
    }


}
