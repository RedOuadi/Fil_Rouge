package com.fitness_management.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness_management.dto.ActiviteDTO;
import com.fitness_management.services.ActiviteService;
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
@RequestMapping("/api/activites")
public class ActiviteController {

    @Autowired
    private ActiviteService activiteService;

    private static final Logger logger = LoggerFactory.getLogger(ActiviteController.class);

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ActiviteDTO> createActivite(
            @RequestPart("activite") String activiteJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestPart(value = "videoFile", required = false) MultipartFile videoFile) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ActiviteDTO activiteDTO = objectMapper.readValue(activiteJson, ActiviteDTO.class);

            ActiviteDTO createdActivite = activiteService.createActivite(activiteDTO, imageFile, videoFile);
            return new ResponseEntity<>(createdActivite, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing activite JSON: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("An error occurred while creating activite: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ActiviteDTO>> getAllActivites() {
        List<ActiviteDTO> activites = activiteService.getAllActivites();
        return new ResponseEntity<>(activites, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActiviteDTO> getActiviteById(@PathVariable Long id) {
        Optional<ActiviteDTO> activite = activiteService.getActiviteById(id);
        return activite.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ActiviteDTO> updateActivite(
            @PathVariable Long id,
            @RequestPart("activite") String activiteJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestPart(value = "videoFile", required = false) MultipartFile videoFile) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ActiviteDTO activiteDTO = objectMapper.readValue(activiteJson, ActiviteDTO.class);

            ActiviteDTO updatedActivite = activiteService.updateActivite(id, activiteDTO, imageFile, videoFile);
            return new ResponseEntity<>(updatedActivite, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing activite JSON: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (EntityNotFoundException e) {
            logger.error("Activite not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("An error occurred while updating activite: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivite(@PathVariable Long id) {
        activiteService.deleteActivite(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
