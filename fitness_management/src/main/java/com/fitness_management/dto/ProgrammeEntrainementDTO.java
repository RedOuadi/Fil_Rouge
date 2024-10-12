package com.fitness_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ProgrammeEntrainementDTO {
    private Long id;
    private String nom;
    private String description;
    private Long coachId;
    private List<ExerciceDTO> exercices;
}