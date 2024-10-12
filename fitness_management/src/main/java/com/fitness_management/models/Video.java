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
public class Video {
    @Id
    @GeneratedValue
    private Long id;
    private String videoUrl;
    private String cloudinaryVideoId;

    @JsonIgnore
    @OneToOne(mappedBy = "ExerciceVideo")
    private Exercice exercice;

    @JsonIgnore
    @OneToMany(mappedBy = "ActiviteVideo")
    private List<Activite> activites;
}