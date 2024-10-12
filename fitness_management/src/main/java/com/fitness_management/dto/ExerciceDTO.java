package com.fitness_management.dto;


import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class ExerciceDTO {
    private Long id;
    private String nom;
    private String description;
    private int duree;
    private String niveau;
    private int caloriesBrulees;
    private ImageDTO exerciceImage;
    private VideoDTO exerciceVideo;
    private Long programmeId;  // Référence facultative à ProgrammeEntrainement par ID

}
