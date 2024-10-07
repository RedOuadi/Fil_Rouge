package com.fitness_management.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Video {
    @Id
    @GeneratedValue
    private Long id;
    private String videoUrl;
    private String cloudinaryVideoId;

}
