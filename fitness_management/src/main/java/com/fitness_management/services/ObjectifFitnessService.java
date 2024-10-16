package com.fitness_management.services;

import com.fitness_management.dto.ObjectifFitnessDTO;
import com.fitness_management.mapper.ObjectifFitnessMapper;
import com.fitness_management.models.ObjectifFitness;
import com.fitness_management.repositories.ObjectifFitnessRepository;
import com.fitness_management.services.interfaces.ObjectifFitnessServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObjectifFitnessService implements ObjectifFitnessServiceI {

    @Autowired
    private ObjectifFitnessRepository objectifFitnessRepository;

    @Autowired
    private ObjectifFitnessMapper objectifFitnessMapper;

    @Override
    public ObjectifFitnessDTO createObjectif(ObjectifFitnessDTO objectifFitnessDTO) {
        ObjectifFitness objectifFitness = objectifFitnessMapper.toEntity(objectifFitnessDTO);
        ObjectifFitness savedObjectifFitness = objectifFitnessRepository.save(objectifFitness);
        return objectifFitnessMapper.toDTO(savedObjectifFitness);
    }

    @Override
    public List<ObjectifFitnessDTO> getAllObjectifs() {
        return objectifFitnessRepository.findAll().stream()
                .map(objectifFitnessMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ObjectifFitnessDTO getObjectifById(Long id) {
        ObjectifFitness objectifFitness = objectifFitnessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Objectif not found with ID: " + id));
        return objectifFitnessMapper.toDTO(objectifFitness);
    }

    @Override
    public ObjectifFitnessDTO updateObjectif(Long id, ObjectifFitnessDTO objectifFitnessDTO) {
        ObjectifFitness existingObjectif = objectifFitnessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Objectif not found with ID: " + id));

        existingObjectif.setType(objectifFitnessDTO.getType());
        existingObjectif.setDescription(objectifFitnessDTO.getDescription());
        existingObjectif.setDateDebut(objectifFitnessDTO.getDateDebut());
        existingObjectif.setDateFin(objectifFitnessDTO.getDateFin());
        existingObjectif.setAtteint(objectifFitnessDTO.isAtteint());

        ObjectifFitness updatedObjectif = objectifFitnessRepository.save(existingObjectif);
        return objectifFitnessMapper.toDTO(updatedObjectif);
    }

    @Override
    public void deleteObjectif(Long id) {
        if (!objectifFitnessRepository.existsById(id)) {
            throw new EntityNotFoundException("Objectif not found with ID: " + id);
        }
        objectifFitnessRepository.deleteById(id);
    }

    @Override
    public List<ObjectifFitnessDTO> getObjectifsByUtilisateurId(Long utilisateurId) {
        return objectifFitnessRepository.findByUtilisateurId(utilisateurId).stream()
                .map(objectifFitnessMapper::toDTO)
                .collect(Collectors.toList());
    }
}
