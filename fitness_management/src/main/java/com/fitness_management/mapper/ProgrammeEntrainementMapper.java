package com.fitness_management.mapper;

import com.fitness_management.dto.ProgrammeEntrainementDTO;
import com.fitness_management.models.ProgrammeEntrainement;
import org.springframework.stereotype.Component;

@Component
public class ProgrammeEntrainementMapper {

    public ProgrammeEntrainementDTO toDto(ProgrammeEntrainement programmeEntrainement) {
        ProgrammeEntrainementDTO dto = new ProgrammeEntrainementDTO();
        dto.setId(programmeEntrainement.getId());
        dto.setNom(programmeEntrainement.getNom());
        dto.setDescription(programmeEntrainement.getDescription());
        return dto;
    }

    public ProgrammeEntrainement toEntity(ProgrammeEntrainementDTO dto) {
        ProgrammeEntrainement programmeEntrainement = new ProgrammeEntrainement();
        programmeEntrainement.setId(dto.getId());
        programmeEntrainement.setNom(dto.getNom());
        programmeEntrainement.setDescription(dto.getDescription());
        return programmeEntrainement;
    }
}
