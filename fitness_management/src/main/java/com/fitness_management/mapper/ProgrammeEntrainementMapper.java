package com.fitness_management.mapper;

import com.fitness_management.dto.ProgrammeEntrainementDTO;
import com.fitness_management.models.Coach;
import com.fitness_management.models.ProgrammeEntrainement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProgrammeEntrainementMapper {

    private final ExerciceMapper exerciceMapper; // Déclarer le mapper

    @Autowired
    public ProgrammeEntrainementMapper(ExerciceMapper exerciceMapper) {
        this.exerciceMapper = exerciceMapper; // Injecter l'instance du mapper
    }

    public ProgrammeEntrainementDTO toDTO(ProgrammeEntrainement programme) {
        ProgrammeEntrainementDTO dto = new ProgrammeEntrainementDTO();
        dto.setId(programme.getId());
        dto.setNom(programme.getNom());
        dto.setDescription(programme.getDescription());
        if (programme.getCoach() != null) {
            dto.setCoachId(programme.getCoach().getId());
        }
        if (programme.getExercices() != null) {
            dto.setExercices(programme.getExercices().stream()
                    .map(exerciceMapper::toDTO) // Utiliser l'instance pour appeler toDTO
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public ProgrammeEntrainement toEntity(ProgrammeEntrainementDTO dto, Coach coach) {
        ProgrammeEntrainement programme = new ProgrammeEntrainement();
        programme.setId(dto.getId());
        programme.setNom(dto.getNom());
        programme.setDescription(dto.getDescription());
        programme.setCoach(coach);
        return programme;
    }
}
