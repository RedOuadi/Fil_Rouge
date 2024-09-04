package com.fitness_management.controllers;

import com.fitness_management.models.Personne;
import com.fitness_management.services.PersonneService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class PersonneController {

    private final PersonneService personneService;

    @Autowired
    public PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }

    // Register a new Personne
    @PostMapping("/register")
    public ResponseEntity<Personne> registerPersonne(@RequestBody Personne personne) {
        Personne registeredPersonne = personneService.registerPersonne(personne);
        return new ResponseEntity<>(registeredPersonne, HttpStatus.CREATED);
    }

    // Login a Personne
    @PostMapping("/login")
    public ResponseEntity<String> loginPersonne(@RequestBody Personne loginDetails) {
        Optional<Personne> authenticatedPersonne = personneService.authenticatePersonne(loginDetails.getEmail(), loginDetails.getMotDePasse());
        if (authenticatedPersonne.isPresent()) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    // Create a new Personne
    @PostMapping
    public ResponseEntity<Personne> createPersonne(@RequestBody Personne personne) {
        Personne createdPersonne = personneService.registerPersonne(personne);
        return new ResponseEntity<>(createdPersonne, HttpStatus.CREATED);
    }

    // Get all Personnes
    @GetMapping
    public ResponseEntity<List<Personne>> getAllPersonnes() {
        List<Personne> personnes = personneService.findAll();
        return new ResponseEntity<>(personnes, HttpStatus.OK);
    }

    // Get a Personne by ID
    @GetMapping("/{id}")
    public ResponseEntity<Personne> getPersonneById(@PathVariable Long id) {
        Optional<Personne> personne = personneService.findById(id);
        return personne.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update a Personne by ID
    @PutMapping("/{id}")
    public ResponseEntity<Personne> updatePersonne(@PathVariable Long id, @RequestBody Personne personneDetails) {
        try {
            Personne updatedPersonne = personneService.updatePersonne(id, personneDetails);
            return new ResponseEntity<>(updatedPersonne, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Personne by ID

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

