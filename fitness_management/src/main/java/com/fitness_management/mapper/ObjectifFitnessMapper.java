package com.fitness_management.mapper;

import com.fitness_management.dto.ObjectifFitnessDTO;
import com.fitness_management.models.ObjectifFitness;
import org.springframework.stereotype.Component;

@Component
public class ObjectifFitnessMapper {

    public ObjectifFitnessDTO toDTO(ObjectifFitness objectifFitness) {
        ObjectifFitnessDTO dto = new ObjectifFitnessDTO();
        dto.setId(objectifFitness.getId());
        dto.setType(objectifFitness.getType());
        dto.setDescription(objectifFitness.getDescription());
        dto.setDateDebut(objectifFitness.getDateDebut());
        dto.setDateFin(objectifFitness.getDateFin());
        dto.setAtteint(objectifFitness.isAtteint());

        if (objectifFitness.getUtilisateur() != null) {
            dto.setUtilisateurId(objectifFitness.getUtilisateur().getId());
        }

        return dto;
    }

    public ObjectifFitness toEntity(ObjectifFitnessDTO dto) {
        ObjectifFitness objectifFitness = new ObjectifFitness();
        objectifFitness.setId(dto.getId());
        objectifFitness.setType(dto.getType());
        objectifFitness.setDescription(dto.getDescription());
        objectifFitness.setDateDebut(dto.getDateDebut());
        objectifFitness.setDateFin(dto.getDateFin());
        objectifFitness.setAtteint(dto.isAtteint());

        return objectifFitness;
    }
}
