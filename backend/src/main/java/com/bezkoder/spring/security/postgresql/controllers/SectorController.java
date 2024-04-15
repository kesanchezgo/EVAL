package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.Sector;
import com.bezkoder.spring.security.postgresql.services.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {
    @Autowired
    private SectorService sectorService;

    @GetMapping
    public ResponseEntity<List<Sector>> getAllSectors() {
        List<Sector> sectors = sectorService.getAllSectors();
        return new ResponseEntity<>(sectors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sector> getSectorById(@PathVariable Long id) {
        Optional<Sector> sector = sectorService.getSectorById(id);
        return sector.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Sector> createSector(@RequestBody Sector sector) {
        Sector createdSector = sectorService.createSector(sector);
        return new ResponseEntity<>(createdSector, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sector> updateSector(@PathVariable Long id, @RequestBody Sector sector) {
        Sector updatedSector = sectorService.updateSector(id, sector);
        if (updatedSector != null) {
            return new ResponseEntity<>(updatedSector, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSector(@PathVariable Long id) {
        sectorService.deleteSector(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
