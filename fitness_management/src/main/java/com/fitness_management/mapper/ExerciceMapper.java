package com.fitness_management.mapper;

import com.fitness_management.dto.ExerciceDTO;
import com.fitness_management.dto.ImageDTO;
import com.fitness_management.dto.VideoDTO;
import com.fitness_management.models.*;
import org.springframework.stereotype.Component;

@Component
public class ExerciceMapper {

    public  ExerciceDTO toDTO(Exercice exercice) {
        if (exercice == null) {
            return null;
        }

        ExerciceDTO dto = new ExerciceDTO();
        dto.setId(exercice.getId());
        dto.setNom(exercice.getNom());
        dto.setDescription(exercice.getDescription());
        dto.setDuree(exercice.getDuree());
        dto.setNiveau(exercice.getNiveau().name());
        dto.setCaloriesBrulees(exercice.getCaloriesBrulees());

        if (exercice.getExerciceImage() != null) {
            dto.setExerciceImage(toImageDto(exercice.getExerciceImage()));
        }

        if (exercice.getExerciceVideo() != null) {
            dto.setExerciceVideo(toVideoDto(exercice.getExerciceVideo()));
        }

        if (exercice.getProgramme() != null) {
            dto.setProgrammeId(exercice.getProgramme().getId());
        }

        return dto;
    }

    public Exercice toEntity(ExerciceDTO dto) {
        if (dto == null) {
            return null;
        }

        Exercice exercice = new Exercice();
        exercice.setId(dto.getId());
        exercice.setNom(dto.getNom());
        exercice.setDescription(dto.getDescription());
        exercice.setDuree(dto.getDuree());
        exercice.setNiveau(Niveau.valueOf(dto.getNiveau().toUpperCase()));  // Conversion de l'énumération `Niveau`

        exercice.setCaloriesBrulees(dto.getCaloriesBrulees());
        // Lier le programme si programmeId est fourni
        if (dto.getProgrammeId() != null) {
            ProgrammeEntrainement programme = new ProgrammeEntrainement();
            programme.setId(dto.getProgrammeId());
            exercice.setProgramme(programme);
        }

        if (dto.getExerciceImage() != null) {
            exercice.setExerciceImage(toImageEntity(dto.getExerciceImage()));
        }

        if (dto.getExerciceVideo() != null) {
            exercice.setExerciceVideo(toVideoEntity(dto.getExerciceVideo()));
        }

        return exercice;
    }

    private ImageDTO toImageDto(Image image) {
        if (image == null) {
            return null;
        }

        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setImageUrl(image.getImageUrl());
        dto.setCloudinaryImageId(image.getCloudinaryImageId());
        return dto;
    }

    private Image toImageEntity(ImageDTO dto) {
        if (dto == null) {
            return null;
        }

        Image image = new Image();
        image.setId(dto.getId());
        image.setImageUrl(dto.getImageUrl());
        image.setCloudinaryImageId(dto.getCloudinaryImageId());
        return image;
    }

    private VideoDTO toVideoDto(Video video) {
        if (video == null) {
            return null;
        }

        VideoDTO dto = new VideoDTO();
        dto.setId(video.getId());
        dto.setVideoUrl(video.getVideoUrl());
        dto.setCloudinaryVideoId(video.getCloudinaryVideoId());
        return dto;
    }

    private Video toVideoEntity(VideoDTO dto) {
        if (dto == null) {
            return null;
        }

        Video video = new Video();
        video.setId(dto.getId());
        video.setVideoUrl(dto.getVideoUrl());
        video.setCloudinaryVideoId(dto.getCloudinaryVideoId());
        return video;
    }
}
