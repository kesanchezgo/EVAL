package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.LineResearch;
import com.bezkoder.spring.security.postgresql.services.LineResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/line_researches")
public class LineResearchController {
    @Autowired
    private LineResearchService lineResearchService;

    @GetMapping
    public ResponseEntity<List<LineResearch>> getAllLineResearches() {
        List<LineResearch> lineResearches = lineResearchService.getAllLineResearches();
        return new ResponseEntity<>(lineResearches, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineResearch> getLineResearchById(@PathVariable Long id) {
        Optional<LineResearch> lineResearch = lineResearchService.getLineResearchById(id);
        return lineResearch.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<LineResearch> createLineResearch(@RequestBody LineResearch lineResearch) {
        LineResearch createdLineResearch = lineResearchService.createLineResearch(lineResearch);
        return new ResponseEntity<>(createdLineResearch, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LineResearch> updateLineResearch(@PathVariable Long id, @RequestBody LineResearch lineResearch) {
        LineResearch updatedLineResearch = lineResearchService.updateLineResearch(id, lineResearch);
        if (updatedLineResearch != null) {
            return new ResponseEntity<>(updatedLineResearch, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLineResearch(@PathVariable Long id) {
        lineResearchService.deleteLineResearch(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
