package dk.au.standservice.service;

import dk.au.standservice.model.Stand;
import dk.au.standservice.repository.StandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StandService {
    private final StandRepository standRepository;

    @Autowired
    public StandService(StandRepository standRepository) {
        this.standRepository = standRepository;
    }

    public List<Stand> getAllStands() {
        return standRepository.findAll();
    }

    public Optional<Stand> getStandById(Long id) {
        return standRepository.findById(id);
    }

    public Stand createStand(Stand stand) {
        return standRepository.save(stand);
    }

    public Stand updateStand(Long id, Stand stand) {
        if (standRepository.existsById(id)) {
            return standRepository.save(stand);
        }
        return null;
    }

    public void deleteStand(Long id) {
        standRepository.deleteById(id);
    }
} 