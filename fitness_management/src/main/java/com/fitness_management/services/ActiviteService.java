package com.fitness_management.services;

import com.fitness_management.dto.ActiviteDTO;
import com.fitness_management.mapper.ActiviteMapper;
import com.fitness_management.models.Activite;
import com.fitness_management.models.Image;
import com.fitness_management.models.Video;
import com.fitness_management.repositories.ActiviteRepository;
import com.fitness_management.models.CloudinaryResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ActiviteService {

    @Autowired
    private ActiviteRepository activiteRepository;

    @Autowired
    private ActiviteMapper activiteMapper;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional
    public ActiviteDTO createActivite(ActiviteDTO activiteDTO, MultipartFile imageFile, MultipartFile videoFile) {
        Activite activite = activiteMapper.toEntity(activiteDTO);

        // Upload image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            CloudinaryResponse imageResponse = cloudinaryService.uploadFile(imageFile, "activite_image", "image");
            if (activite.getActiviteImage() == null) {
                activite.setActiviteImage(new Image());
            }
            activite.getActiviteImage().setImageUrl(imageResponse.getUrl());
            activite.getActiviteImage().setCloudinaryImageId(imageResponse.getPublicId());
        }

        // Upload video if provided
        if (videoFile != null && !videoFile.isEmpty()) {
            CloudinaryResponse videoResponse = cloudinaryService.uploadFile(videoFile, "activite_video", "video");
            if (activite.getActiviteVideo() == null) {
                activite.setActiviteVideo(new Video());
            }
            activite.getActiviteVideo().setVideoUrl(videoResponse.getUrl());
            activite.getActiviteVideo().setCloudinaryVideoId(videoResponse.getPublicId());
        }

        Activite savedActivite = activiteRepository.save(activite);
        return activiteMapper.toDTO(savedActivite);
    }

    public List<ActiviteDTO> getAllActivites() {
        List<Activite> activites = activiteRepository.findAll();
        return activites.stream().map(activiteMapper::toDTO).toList();
    }

    public Optional<ActiviteDTO> getActiviteById(Long id) {
        return activiteRepository.findById(id).map(activiteMapper::toDTO);
    }

    @Transactional
    public ActiviteDTO updateActivite(Long id, ActiviteDTO activiteDTO, MultipartFile imageFile, MultipartFile videoFile) {
        Activite existingActivite = activiteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activite not found with id: " + id));

        // Update fields
        existingActivite.setDate(activiteDTO.getDate());
        existingActivite.setPas(activiteDTO.getPas());
        existingActivite.setDistance(activiteDTO.getDistance());
        existingActivite.setCaloriesBrulees(activiteDTO.getCaloriesBrulees());

        // Update image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            CloudinaryResponse imageResponse = cloudinaryService.uploadFile(imageFile, "activite_image", "image");
            if (existingActivite.getActiviteImage() != null) {
                existingActivite.getActiviteImage().setImageUrl(imageResponse.getUrl());
                existingActivite.getActiviteImage().setCloudinaryImageId(imageResponse.getPublicId());
            } else {
                Image newImage = new Image();
                newImage.setImageUrl(imageResponse.getUrl());
                newImage.setCloudinaryImageId(imageResponse.getPublicId());
                existingActivite.setActiviteImage(newImage);
            }
        }

        // Update video if provided
        if (videoFile != null && !videoFile.isEmpty()) {
            CloudinaryResponse videoResponse = cloudinaryService.uploadFile(videoFile, "activite_video", "video");
            if (existingActivite.getActiviteVideo() != null) {
                existingActivite.getActiviteVideo().setVideoUrl(videoResponse.getUrl());
                existingActivite.getActiviteVideo().setCloudinaryVideoId(videoResponse.getPublicId());
            } else {
                Video newVideo = new Video();
                newVideo.setVideoUrl(videoResponse.getUrl());
                newVideo.setCloudinaryVideoId(videoResponse.getPublicId());
                existingActivite.setActiviteVideo(newVideo);
            }
        }

        Activite updatedActivite = activiteRepository.save(existingActivite);
        return activiteMapper.toDTO(updatedActivite);
    }

    public void deleteActivite(Long id) {
        activiteRepository.deleteById(id);
    }
}

