package com.fitness_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ActiviteDTO {
    private Long id;
    private Date date;
    private int pas;
    private double distance;
    private int caloriesBrulees;
    private Long utilisateurId;  // Référence à l'utilisateur par ID
    private ImageDTO activiteImage;
    private VideoDTO activiteVideo;
}
