package com.fitness_management.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VideoDTO {

    @Id
    @GeneratedValue
    private Long id;
    private String videoUrl;
    private String cloudinaryVideoId;
}
