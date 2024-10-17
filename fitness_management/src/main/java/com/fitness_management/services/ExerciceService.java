package com.fitness_management.services;

import com.fitness_management.dto.ExerciceDTO;
import com.fitness_management.mapper.ExerciceMapper;
import com.fitness_management.models.*;
import com.fitness_management.repositories.ExerciceRepository;
import com.fitness_management.repositories.ProgrammeEntrainementRepository;
import com.fitness_management.services.interfaces.ExerciceServiceI;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciceService implements ExerciceServiceI {

    @Autowired
    private ExerciceRepository exerciceRepository;

    @Autowired
    private ExerciceMapper exerciceMapper;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ProgrammeEntrainementRepository programmeEntrainementRepository;

    @Override
    @Transactional
    public ExerciceDTO createExercice(ExerciceDTO exerciceDTO, MultipartFile imageFile, MultipartFile videoFile) {
        Exercice exercice = exerciceMapper.toEntity(exerciceDTO);

        // Upload image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            CloudinaryResponse imageResponse = cloudinaryService.uploadFile(imageFile, "exercice_image", "image");
            if (exercice.getExerciceImage() == null) {
                exercice.setExerciceImage(new Image());
            }
            exercice.getExerciceImage().setImageUrl(imageResponse.getUrl());
            exercice.getExerciceImage().setCloudinaryImageId(imageResponse.getPublicId());
        }

        // Upload video if provided
        if (videoFile != null && !videoFile.isEmpty()) {
            CloudinaryResponse videoResponse = cloudinaryService.uploadFile(videoFile, "exercice_video", "video");
            if (exercice.getExerciceVideo() == null) {
                exercice.setExerciceVideo(new Video());
            }
            exercice.getExerciceVideo().setVideoUrl(videoResponse.getUrl());
            exercice.getExerciceVideo().setCloudinaryVideoId(videoResponse.getPublicId());
        }

        Exercice savedExercice = exerciceRepository.save(exercice);
        return exerciceMapper.toDTO(savedExercice);
    }

    @Override
    public List<ExerciceDTO> getAllExercices() {
        List<Exercice> exercices = exerciceRepository.findAll();
        return exercices.stream().map(exerciceMapper::toDTO).toList();
    }

    @Override
    public Optional<ExerciceDTO> getExerciceById(Long id) {
        return exerciceRepository.findById(id).map(exerciceMapper::toDTO);
    }

    @Override
    public List<ExerciceDTO> getExercicesByProgrammeId(Long programmeId) {
        List<Exercice> exercices = exerciceRepository.findByProgrammeId(programmeId);
        return exercices.stream().map(exerciceMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public ExerciceDTO updateExercice(Long id, ExerciceDTO exerciceDTO, MultipartFile imageFile, MultipartFile videoFile) {
        Exercice existingExercice = exerciceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exercice not found with id: " + id));

        existingExercice.setNom(exerciceDTO.getNom());
        existingExercice.setDescription(exerciceDTO.getDescription());
        existingExercice.setDuree(exerciceDTO.getDuree());
        existingExercice.setNiveau(Niveau.valueOf(exerciceDTO.getNiveau().toUpperCase()));
        existingExercice.setCaloriesBrulees(exerciceDTO.getCaloriesBrulees());

        // Update image if necessary
        if (imageFile != null && !imageFile.isEmpty()) {
            CloudinaryResponse imageResponse = cloudinaryService.uploadFile(imageFile, "exercice_image", "image");
            if (existingExercice.getExerciceImage() != null) {
                existingExercice.getExerciceImage().setImageUrl(imageResponse.getUrl());
                existingExercice.getExerciceImage().setCloudinaryImageId(imageResponse.getPublicId());
            } else {
                Image newImage = new Image();
                newImage.setImageUrl(imageResponse.getUrl());
                newImage.setCloudinaryImageId(imageResponse.getPublicId());
                existingExercice.setExerciceImage(newImage);
            }
        }

        // Update video if necessary
        if (videoFile != null && !videoFile.isEmpty()) {
            CloudinaryResponse videoResponse = cloudinaryService.uploadFile(videoFile, "exercice_video", "video");
            if (existingExercice.getExerciceVideo() != null) {
                existingExercice.getExerciceVideo().setVideoUrl(videoResponse.getUrl());
                existingExercice.getExerciceVideo().setCloudinaryVideoId(videoResponse.getPublicId());
            } else {
                Video newVideo = new Video();
                newVideo.setVideoUrl(videoResponse.getUrl());
                newVideo.setCloudinaryVideoId(videoResponse.getPublicId());
                existingExercice.setExerciceVideo(newVideo);
            }
        }

        Exercice updatedExercice = exerciceRepository.save(existingExercice);
        return exerciceMapper.toDTO(updatedExercice);
    }

    @Override
    public void deleteExercice(Long id) {
        exerciceRepository.deleteById(id);
    }

    @Override
    public int countExercice() {
        return exerciceRepository.cont();
    }


}
