package com.fitness_management.mapper;

import com.fitness_management.dto.ActiviteDTO;
import com.fitness_management.dto.ImageDTO;
import com.fitness_management.dto.VideoDTO;
import com.fitness_management.models.*;
import org.springframework.stereotype.Component;

@Component
public class ActiviteMapper {

    public ActiviteDTO toDTO(Activite activite) {
        if (activite == null) {
            return null;
        }

        ActiviteDTO dto = new ActiviteDTO();
        dto.setId(activite.getId());
        dto.setDate(activite.getDate());
        dto.setPas(activite.getPas());
        dto.setDistance(activite.getDistance());
        dto.setCaloriesBrulees(activite.getCaloriesBrulees());

        if (activite.getUtilisateur() != null) {
            dto.setUtilisateurId(activite.getUtilisateur().getId());
        }

        if (activite.getActiviteImage() != null) {
            dto.setActiviteImage(toImageDto(activite.getActiviteImage()));
        }

        if (activite.getActiviteVideo() != null) {
            dto.setActiviteVideo(toVideoDto(activite.getActiviteVideo()));
        }

        return dto;
    }

    public Activite toEntity(ActiviteDTO dto) {
        if (dto == null) {
            return null;
        }

        Activite activite = new Activite();
        activite.setId(dto.getId());
        activite.setDate(dto.getDate());
        activite.setPas(dto.getPas());
        activite.setDistance(dto.getDistance());
        activite.setCaloriesBrulees(dto.getCaloriesBrulees());

        // Lier l'utilisateur si utilisateurId est fourni
        if (dto.getUtilisateurId() != null) {
            User utilisateur = new User();
            utilisateur.setId(dto.getUtilisateurId());
            activite.setUtilisateur(utilisateur);
        }

        if (dto.getActiviteImage() != null) {
            activite.setActiviteImage(toImageEntity(dto.getActiviteImage()));
        }

        if (dto.getActiviteVideo() != null) {
            activite.setActiviteVideo(toVideoEntity(dto.getActiviteVideo()));
        }

        return activite;
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

