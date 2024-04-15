package com.bezkoder.spring.security.postgresql.services;
import com.bezkoder.spring.security.postgresql.models.LineResearch;
import com.bezkoder.spring.security.postgresql.repository.LineResearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LineResearchService {
    @Autowired
    private LineResearchRepository lineResearchRepository;

    public List<LineResearch> getAllLineResearches() {
        return lineResearchRepository.findAll();
    }

    public Optional<LineResearch> getLineResearchById(Long id) {
        return lineResearchRepository.findById(id);
    }

    public LineResearch createLineResearch(LineResearch lineResearch) {
        return lineResearchRepository.save(lineResearch);
    }

    public LineResearch updateLineResearch(Long id, LineResearch lineResearchDetails) {
        Optional<LineResearch> optionalLineResearch = lineResearchRepository.findById(id);
        if (optionalLineResearch.isPresent()) {
            LineResearch lineResearch = optionalLineResearch.get();
            lineResearch.setName(lineResearchDetails.getName());
            // Actualiza otros atributos si es necesario
            return lineResearchRepository.save(lineResearch);
        } else {
            return null; // Manejar el caso de que la línea de investigación no exista
        }
    }

    public void deleteLineResearch(Long id) {
        lineResearchRepository.deleteById(id);
    }
}
