package com.fitness_management.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness_management.dto.ExerciceDTO;
import com.fitness_management.services.ExerciceService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/exercices")
public class ExerciceController {

    @Autowired
    private ExerciceService exerciceService;

    private static final Logger logger = LoggerFactory.getLogger(ExerciceController.class);

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExerciceDTO> createExercice(
            @RequestPart("exercice") String exerciceJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestPart(value = "videoFile", required = false) MultipartFile videoFile) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ExerciceDTO exerciceDTO = objectMapper.readValue(exerciceJson, ExerciceDTO.class);

            ExerciceDTO createdExercice = exerciceService.createExercice(exerciceDTO, imageFile, videoFile);
            return new ResponseEntity<>(createdExercice, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing exercice JSON: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("An error occurred while creating exercice: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ExerciceDTO>> getAllExercices() {
        List<ExerciceDTO> exercices = exerciceService.getAllExercices();
        return new ResponseEntity<>(exercices, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciceDTO> getExerciceById(@PathVariable Long id) {
        Optional<ExerciceDTO> exercice = exerciceService.getExerciceById(id);
        return exercice.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/programme/{programmeId}")
    public ResponseEntity<List<ExerciceDTO>> getExercicesByProgrammeId(@PathVariable Long programmeId) {
        List<ExerciceDTO> exercices = exerciceService.getExercicesByProgrammeId(programmeId);
        if (exercices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(exercices, HttpStatus.OK);
    }



    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExerciceDTO> updateExercice(
            @PathVariable Long id,
            @RequestPart("exercice") String exerciceJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestPart(value = "videoFile", required = false) MultipartFile videoFile) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ExerciceDTO exerciceDTO = objectMapper.readValue(exerciceJson, ExerciceDTO.class);

            ExerciceDTO updatedExercice = exerciceService.updateExercice(id, exerciceDTO, imageFile, videoFile);
            return new ResponseEntity<>(updatedExercice, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing exercice JSON: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (EntityNotFoundException e) {
            logger.error("Exercice not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("An error occurred while updating exercice: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercice(@PathVariable Long id) {
        exerciceService.deleteExercice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
