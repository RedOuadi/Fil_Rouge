package com.fitness_management.controllers;

import com.fitness_management.models.Personne;
import com.fitness_management.models.Role;
import com.fitness_management.security.JwtAuth;
import com.fitness_management.services.PersonneService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class PersonneController {

    private final PersonneService personneService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuth jwtAuth;


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
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> userRequest) {
        String email = userRequest.get("email");
        String rawPassword = userRequest.get("motDePasse");

        if (email == null || rawPassword == null) {
            return ResponseEntity.badRequest().body("Email and password are required");
        }

        Optional<Personne> optionalUser = personneService.findByEmail(email);
        if (optionalUser.isPresent()) {
            Personne foundUser = optionalUser.get();
            String encodedPassword = foundUser.getMotDePasse();

            if (passwordEncoder.matches(rawPassword, encodedPassword)) {
                Role role = foundUser.getRole();
                String token = jwtAuth.generateToken(foundUser.getEmail(), String.valueOf(role));

                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("role", String.valueOf(role));

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }
        }
        return ResponseEntity.status(401).body("Invalid email or password");
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

