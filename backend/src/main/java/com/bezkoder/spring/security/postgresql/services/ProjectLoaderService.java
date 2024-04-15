package com.bezkoder.spring.security.postgresql.services;

import com.bezkoder.spring.security.postgresql.models.Evaluation;
import com.bezkoder.spring.security.postgresql.models.Project;
import com.bezkoder.spring.security.postgresql.models.ProjectEvaluation;
import com.bezkoder.spring.security.postgresql.payload.request.ExternalProject;
import com.bezkoder.spring.security.postgresql.repository.EvaluationRepository;
import com.bezkoder.spring.security.postgresql.repository.ProjectEvaluationRepository;
import com.bezkoder.spring.security.postgresql.repository.ProjectRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectLoaderService {

    @Value("${sismo.url}")
    private String sismoUrl;

    private final ProjectRepository projectRepository;
    private final ProjectEvaluationRepository projectEvaluationRepository;
    private final EvaluationRepository evaluationRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Autowired
    public ProjectLoaderService(ProjectRepository projectRepository, ProjectEvaluationRepository projectEvaluationRepository, EvaluationRepository evaluationRepository) {
        this.projectRepository = projectRepository;
        this.projectEvaluationRepository = projectEvaluationRepository;
        this.evaluationRepository = evaluationRepository;
    }
    public void loadProjectsFromSismo(Long evaluationId) {
        String url = sismoUrl + "/duginf/proyectos-concluidos";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String jsonData = response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            List<ExternalProject> externalProjects = objectMapper.readValue(jsonData, new TypeReference<List<ExternalProject>>() {});

            for (ExternalProject externalProject : externalProjects) {
                Project existingProject = projectRepository.findByExternalId("SISMO" + externalProject.getId_proyecto());

                if (existingProject != null) {
                    existingProject.setName(externalProject.getNomb_proy());
                    existingProject.setAuthor(externalProject.getPrincipal());
                    existingProject.setStatus(externalProject.getEst_proy());
                    existingProject.setArea1(externalProject.getArea_investigacion());
                    existingProject.setArea2(externalProject.getArea_ocde());

                    projectRepository.save(existingProject);
                } else {
                    Project newProject = new Project();
                    newProject.setExternalId("SISMO-" + externalProject.getId_proyecto());
                    newProject.setName(externalProject.getNomb_proy());
                    newProject.setAuthor(externalProject.getPrincipal());
                    newProject.setStatus(externalProject.getEst_proy());
                    newProject.setArea1(externalProject.getArea_investigacion());
                    newProject.setArea2(externalProject.getArea_ocde());
                    newProject.setCondition("N");

                    projectRepository.save(newProject);
                    // Crear una evaluación para el nuevo proyecto con puntaje inicial de 0
                    Optional<Evaluation> evaluation = evaluationRepository.findById(evaluationId);
                    //System.out.println(evaluation.get().getName());
                    // Crear una evaluación para el nuevo proyecto con puntaje inicial de 0
                    createProjectEvaluation(newProject, evaluation.orElse(null));
                }
            }
        } catch (IOException e) {
            // Manejo de errores de lectura de datos JSON
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar los datos JSON", e);
        } catch (HttpClientErrorException e) {
            // Manejo de errores de HTTP (por ejemplo, 404 Not Found)
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la solicitud HTTP", e);
        }
    }


    private void createProjectEvaluation(Project project, Evaluation evaluation) {
        
        // Crear una nueva evaluación para el proyecto con puntaje inicial de 0
        ProjectEvaluation projectEvaluation = new ProjectEvaluation();
        projectEvaluation.setProject(project);
        projectEvaluation.setEvaluation(evaluation);
        projectEvaluation.setScore(0.0); // Puntaje inicial de 0

        // Guardar la evaluación
        projectEvaluationRepository.save(projectEvaluation);
    }
    
}
