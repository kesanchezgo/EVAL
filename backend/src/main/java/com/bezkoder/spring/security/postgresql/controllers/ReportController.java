package com.bezkoder.spring.security.postgresql.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bezkoder.spring.security.postgresql.services.ReportService;

@CrossOrigin(origins = {"http://localhost:4200/", "http://vri.gestioninformacion.unsa.edu.pe/}"})
@RequestMapping("/api")
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    /* @PostMapping("/generate-report")
    public String generateReport(@RequestBody Long projectId) {
        return reportService.generateReport(projectId);
    } */

    @GetMapping("/generate-report")
    public String generateReport(@RequestParam Long projectId) {
        return reportService.generateReport(projectId);
    }
}
