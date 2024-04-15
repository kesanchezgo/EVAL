package com.bezkoder.spring.security.postgresql.services;

import com.bezkoder.spring.security.postgresql.models.Sector;
import com.bezkoder.spring.security.postgresql.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorService {
    @Autowired
    private SectorRepository sectorRepository;

    public List<Sector> getAllSectors() {
        return sectorRepository.findAll();
    }

    public Optional<Sector> getSectorById(Long id) {
        return sectorRepository.findById(id);
    }

    public Sector createSector(Sector sector) {
        return sectorRepository.save(sector);
    }

    public Sector updateSector(Long id, Sector sectorDetails) {
        Optional<Sector> optionalSector = sectorRepository.findById(id);
        if (optionalSector.isPresent()) {
            Sector sector = optionalSector.get();
            sector.setName(sectorDetails.getName());
            // Actualiza otros atributos si es necesario
            return sectorRepository.save(sector);
        } else {
            return null; // Manejar el caso de que el sector no exista
        }
    }

    public void deleteSector(Long id) {
        sectorRepository.deleteById(id);
    }
}
