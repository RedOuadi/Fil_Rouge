package com.fitness_management.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ObjectifFitnessDTO {
    private Long id;
    private String type;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private boolean atteint;
    private Long utilisateurId; // Si vous souhaitez inclure l'ID de l'utilisateur
}
