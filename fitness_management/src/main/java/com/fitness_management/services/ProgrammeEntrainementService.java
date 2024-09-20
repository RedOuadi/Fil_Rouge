package com.fitness_management.services;

import com.fitness_management.models.ProgrammeEntrainement;
import com.fitness_management.repositories.ProgrammeEntrainementRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgrammeEntrainementService {

    @Autowired
    private ProgrammeEntrainementRepository programmeEntrainementRepository;

    @Transactional
    public ProgrammeEntrainement save(ProgrammeEntrainement programmeEntrainement) {
        return programmeEntrainementRepository.save(programmeEntrainement);
    }

    public List<ProgrammeEntrainement> findAll() {
        return programmeEntrainementRepository.findAll();
    }

    public Optional<ProgrammeEntrainement> findById(Long id) {
        return programmeEntrainementRepository.findById(id);
    }

    @Transactional
    public ProgrammeEntrainement update(Long id, ProgrammeEntrainement updatedProgrammeEntrainement) {
        Optional<ProgrammeEntrainement> optionalProgramme = findById(id);
        if (optionalProgramme.isPresent()) {
            ProgrammeEntrainement existingProgramme = optionalProgramme.get();

            existingProgramme.setNom(updatedProgrammeEntrainement.getNom());
            existingProgramme.setDescription(updatedProgrammeEntrainement.getDescription());

            return programmeEntrainementRepository.save(existingProgramme);
        }
        throw new EntityNotFoundException("ProgrammeEntrainement not found with id: " + id);
    }

    public void delete(Long id) {
        programmeEntrainementRepository.deleteById(id);
    }
}

