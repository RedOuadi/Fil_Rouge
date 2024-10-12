package com.fitness_management.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness_management.dto.PersonneDTO;
import com.fitness_management.dto.ProgrammeEntrainementDTO;
import com.fitness_management.models.Genre;
import com.fitness_management.models.Personne;
import com.fitness_management.models.Role;
import com.fitness_management.services.PersonneService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
@CrossOrigin(origins = "http://localhost:4200/")

public class CoachController {
    private final PersonneService personneService;
    private static final Logger logger = LoggerFactory.getLogger(CoachController.class);

    public CoachController(PersonneService personneService) {
        this.personneService = personneService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PersonneDTO> registerPersonne(
            @RequestPart("personne") String personneJson,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {

        logger.info("Received POST request to register a new coach");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PersonneDTO personneDTO = objectMapper.readValue(personneJson, PersonneDTO.class);
            personneDTO.setRole(Role.ROLE_COACH.toString());

            PersonneDTO registeredCoach = personneService.registerPersonne(personneDTO, profileImage);
            logger.info("Coach successfully registered with email: {}", personneDTO.getEmail());

            return new ResponseEntity<>(registeredCoach, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error registering coach: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping
    public ResponseEntity<List<Personne>> getAllCoaches() {
        List<Personne> coaches = personneService.getCoaches();
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personne> getCoachById(@PathVariable Long id) {
        return personneService.findById(id)
                .map(coach -> new ResponseEntity<>(coach, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PersonneDTO> updateCoach(
            @PathVariable Long id,
            @RequestPart("personne") String personneJson,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {

        logger.info("Received PUT request to update coach with ID: {}", id);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PersonneDTO personneDTO = objectMapper.readValue(personneJson, PersonneDTO.class);
            personneDTO.setRole(Role.ROLE_COACH.toString());

            PersonneDTO updatedCoach = personneService.updatePersonne(id, personneDTO, profileImage);
            logger.info("Coach with ID: {} successfully updated.", id);

            return new ResponseEntity<>(updatedCoach, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating coach: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoach(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}