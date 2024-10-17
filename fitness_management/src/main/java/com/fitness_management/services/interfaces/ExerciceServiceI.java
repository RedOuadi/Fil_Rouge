package com.fitness_management.services.interfaces;

import com.fitness_management.dto.ExerciceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ExerciceServiceI {

    ExerciceDTO createExercice(ExerciceDTO exerciceDTO, MultipartFile imageFile, MultipartFile videoFile);

    List<ExerciceDTO> getAllExercices();

    Optional<ExerciceDTO> getExerciceById(Long id);

    List<ExerciceDTO> getExercicesByProgrammeId(Long programmeId);

    ExerciceDTO updateExercice(Long id, ExerciceDTO exerciceDTO, MultipartFile imageFile, MultipartFile videoFile);

    void deleteExercice(Long id);

    int  countExercice();
}
