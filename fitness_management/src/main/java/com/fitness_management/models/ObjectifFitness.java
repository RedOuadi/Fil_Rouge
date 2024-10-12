package com.fitness_management.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ObjectifFitness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private boolean atteint;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private User utilisateur;
}