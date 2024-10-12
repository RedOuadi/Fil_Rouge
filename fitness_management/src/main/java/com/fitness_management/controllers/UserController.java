package com.fitness_management.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness_management.dto.PersonneDTO;
import com.fitness_management.models.Genre;
import com.fitness_management.models.Personne;
import com.fitness_management.models.Role;
import com.fitness_management.services.PersonneService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {
    private final PersonneService personneService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(PersonneService personneService) {
        this.personneService = personneService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PersonneDTO> registerPersonne(
            @RequestPart("personne") String personneJson,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        logger.info("Received POST request to register a new user");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PersonneDTO personneDTO = objectMapper.readValue(personneJson, PersonneDTO.class);
            personneDTO.setRole(Role.ROLE_UTILISATEUR.toString());

            PersonneDTO registeredUser = personneService.registerPersonne(personneDTO, profileImage);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error registering user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping
    public ResponseEntity<List<Personne>> getAllUsers() {
        List<Personne> users = personneService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personne> getUserById(@PathVariable Long id) {
        return personneService.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PersonneDTO> updatePersonne(
            @PathVariable Long id,
            @RequestPart("personne") String personneJson,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {

        logger.info("Received PUT request to update user with ID: {}", id);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PersonneDTO personneDTO = objectMapper.readValue(personneJson, PersonneDTO.class);
            personneDTO.setRole(Role.ROLE_UTILISATEUR.toString());

            PersonneDTO updatedUser = personneService.updatePersonne(id, personneDTO, profileImage);
            logger.info("User with ID: {} successfully updated.", id);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}