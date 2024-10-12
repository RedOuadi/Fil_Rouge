package com.fitness_management.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String cloudinaryImageId;

    @JsonIgnore
    @OneToOne(mappedBy = "profileImage")
    private Personne personne;

    @JsonIgnore
    @OneToOne(mappedBy = "ExerciceImage")
    private Exercice exercice;

    @JsonIgnore
    @OneToMany(mappedBy = "ActiviteImage")
    private List<Activite> activites;
}