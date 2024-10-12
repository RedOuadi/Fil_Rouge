package com.fitness_management.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Exercice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private int duree;

    @Enumerated(EnumType.STRING)
    private Niveau niveau;

    private int caloriesBrulees;

    @OneToOne(cascade = CascadeType.ALL)
    private Image ExerciceImage;

    @OneToOne(cascade = CascadeType.ALL)
    private Video ExerciceVideo;

    @ManyToOne
    @JoinColumn(name = "programme_id")
    private ProgrammeEntrainement programme;
}
