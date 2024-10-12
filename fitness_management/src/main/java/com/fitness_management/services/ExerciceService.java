package com.fitness_management.services;

import com.fitness_management.dto.ExerciceDTO;
import com.fitness_management.mapper.ExerciceMapper;
import com.fitness_management.models.*;
import com.fitness_management.repositories.ExerciceRepository;
import com.fitness_management.repositories.ProgrammeEntrainementRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciceService {

    @Autowired
    private ExerciceRepository exerciceRepository;

    @Autowired
    private ExerciceMapper exerciceMapper;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ProgrammeEntrainementRepository programmeEntrainementRepository;
    @Transactional
    public ExerciceDTO createExercice(ExerciceDTO exerciceDTO, MultipartFile imageFile, MultipartFile videoFile) {
        // Convertir le DTO en entité
        Exercice exercice = exerciceMapper.toEntity(exerciceDTO);

        // Vérifier et uploader l'image sur Cloudinary si fournie
        if (imageFile != null && !imageFile.isEmpty()) {
            CloudinaryResponse imageResponse = cloudinaryService.uploadFile(imageFile, "exercice_image", "image");

            // S'assurer que l'objet Image est créé
            if (exercice.getExerciceImage() == null) {
                exercice.setExerciceImage(new Image());
            }

            // Définir les propriétés de l'image
            exercice.getExerciceImage().setImageUrl(imageResponse.getUrl());
            exercice.getExerciceImage().setCloudinaryImageId(imageResponse.getPublicId());
        }

        // Vérifier et uploader la vidéo sur Cloudinary si fournie
        if (videoFile != null && !videoFile.isEmpty()) {
            CloudinaryResponse videoResponse = cloudinaryService.uploadFile(videoFile, "exercice_video", "video");

            // S'assurer que l'objet Video est créé
            if (exercice.getExerciceVideo() == null) {
                exercice.setExerciceVideo(new Video());
            }

            // Définir les propriétés de la vidéo
            exercice.getExerciceVideo().setVideoUrl(videoResponse.getUrl());
            exercice.getExerciceVideo().setCloudinaryVideoId(videoResponse.getPublicId());
        }

        // Sauvegarder l'exercice
        Exercice savedExercice = exerciceRepository.save(exercice);
        return exerciceMapper.toDTO(savedExercice);
    }

    public List<ExerciceDTO> getAllExercices() {
        List<Exercice> exercices = exerciceRepository.findAll();
        return exercices.stream().map(exerciceMapper::toDTO).toList();
    }

    public Optional<ExerciceDTO> getExerciceById(Long id) {
        return exerciceRepository.findById(id).map(exerciceMapper::toDTO);
    }

    public List<ExerciceDTO> getExercicesByProgrammeId(Long programmeId) {
        List<Exercice> exercices = exerciceRepository.findByProgrammeId(programmeId);
        return exercices.stream().map(exerciceMapper::toDTO).toList();
    }


    @Transactional
    public ExerciceDTO updateExercice(Long id, ExerciceDTO exerciceDTO, MultipartFile imageFile, MultipartFile videoFile) {
        // Recherche de l'exercice existant
        Exercice existingExercice = exerciceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exercice not found with id: " + id));

        // Mise à jour des champs de l'exercice directement
        existingExercice.setNom(exerciceDTO.getNom());
        existingExercice.setDescription(exerciceDTO.getDescription());
        existingExercice.setDuree(exerciceDTO.getDuree());
        existingExercice.setNiveau(Niveau.valueOf(exerciceDTO.getNiveau().toUpperCase()));  // Conversion de l'énumération
        existingExercice.setCaloriesBrulees(exerciceDTO.getCaloriesBrulees());

        // Mise à jour de l'image si nécessaire
        if (imageFile != null && !imageFile.isEmpty()) {
            CloudinaryResponse imageResponse = cloudinaryService.uploadFile(imageFile, "exercice_image", "image");
            if (existingExercice.getExerciceImage() != null) {
                // Mise à jour de l'image existante
                existingExercice.getExerciceImage().setImageUrl(imageResponse.getUrl());
                existingExercice.getExerciceImage().setCloudinaryImageId(imageResponse.getPublicId());
            } else {
                // Création d'une nouvelle image si elle n'existe pas encore
                Image newImage = new Image();
                newImage.setImageUrl(imageResponse.getUrl());
                newImage.setCloudinaryImageId(imageResponse.getPublicId());
                existingExercice.setExerciceImage(newImage);
            }
        }

        // Mise à jour de la vidéo si nécessaire
        if (videoFile != null && !videoFile.isEmpty()) {
            CloudinaryResponse videoResponse = cloudinaryService.uploadFile(videoFile, "exercice_video", "video");
            if (existingExercice.getExerciceVideo() != null) {
                // Mise à jour de la vidéo existante
                existingExercice.getExerciceVideo().setVideoUrl(videoResponse.getUrl());
                existingExercice.getExerciceVideo().setCloudinaryVideoId(videoResponse.getPublicId());
            } else {
                // Création d'une nouvelle vidéo si elle n'existe pas encore
                Video newVideo = new Video();
                newVideo.setVideoUrl(videoResponse.getUrl());
                newVideo.setCloudinaryVideoId(videoResponse.getPublicId());
                existingExercice.setExerciceVideo(newVideo);
            }
        }

        // Sauvegarde de l'exercice mis à jour
        Exercice updatedExercice = exerciceRepository.save(existingExercice);

        // Retour du DTO mis à jour
        return exerciceMapper.toDTO(updatedExercice);
    }

    public void deleteExercice(Long id) {
        exerciceRepository.deleteById(id);
    }
}
