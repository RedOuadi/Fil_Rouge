package com.fitness_management.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness_management.dto.PersonneDTO;
import com.fitness_management.models.Genre;
import com.fitness_management.models.Personne;
import com.fitness_management.models.Role;
import com.fitness_management.security.JwtAuth;
import com.fitness_management.services.PersonneService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/personnes")
public class PersonneController {

    private final PersonneService personneService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuth jwtAuth;


    private static final Logger logger = LoggerFactory.getLogger(PersonneController.class);

    public PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }

    // Register a new Personne
    // Register a new Personne with Multipart support
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PersonneDTO> registerPersonne(
            @RequestPart("personne") String personneJson,  // Use String to receive raw JSON for deserialization
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {

        // Log the received request
        logger.info("Received POST request to register a new personne");

        // Log the image information
        if (profileImage != null) {
            logger.info("Profile image received with size: {}", profileImage.getSize());
        } else {
            logger.info("No profile image received");
        }

        PersonneDTO personneDTO = null;  // Declare personneDTO before the try block

        try {
            // Parse the JSON string into a PersonneDTO object using ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            personneDTO = objectMapper.readValue(personneJson, PersonneDTO.class);
            Role.valueOf(personneDTO.getRole());
            Genre.valueOf(personneDTO.getGenre());

            // Log the parsed data
            logger.info("Personne details: Email: {}", personneDTO.getEmail());

            // Register the personne
            PersonneDTO registeredPersonne = personneService.registerPersonne(personneDTO, profileImage);

            // Log success
            logger.info("Personne with email: {} successfully registered.", personneDTO.getEmail());

            // Return successful response
            return new ResponseEntity<>(registeredPersonne, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            logger.error("Invalid role: {}", personneDTO.getRole());
            return ResponseEntity.badRequest().body(null);
        }
        catch (JsonProcessingException e) {
            // Handle JSON parsing errors
            logger.error("Error parsing personne JSON: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);

        } catch (MultipartException e) {
            // Handle multipart/form-data specific exceptions
            logger.error("Multipart error occurred while uploading profile image: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (ValidationException e) {
            // Handle validation errors
            logger.error("Validation error for personne with email: {}: {}",
                    personneDTO != null ? personneDTO.getEmail() : "unknown", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            // Handle general exceptions
            logger.error("An error occurred while registering personne: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdminUser() {
        logger.info("Received request to create admin user.");

        try {
            personneService.createAdminUserIfNotExist();
            logger.info("Admin user created successfully.");
            return ResponseEntity.ok("Admin user created successfully");
        } catch (Exception e) {
            logger.error("Error occurred while creating admin user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating admin user.");
        }
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

            // Check if the password matches
            if (passwordEncoder.matches(rawPassword, encodedPassword)) {
                Role role = foundUser.getRole();
                String token = jwtAuth.generateToken(foundUser.getEmail(), String.valueOf(role));

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("role", String.valueOf(role));
                if (role.equals(Role.ROLE_COACH)) { // For a coach
                    response.put("coachId", foundUser.getId());
                } else if (role.equals(Role.ROLE_UTILISATEUR)) {
                    response.put("userId", foundUser.getId());
                } else {
                    response.put("adminId", foundUser.getId());
                }

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }





    // Get all Personnes
    @GetMapping
    public ResponseEntity<List<Personne>> getAllPersonnes() {
        List<Personne> personnes = personneService.findAll();
        
        return new ResponseEntity<>(personnes, HttpStatus.OK);
    }
    @GetMapping("/hello")
    public String test (){
        return "hello world";
    }

    // Get a Personne by ID
    @GetMapping("/{id}")
    public ResponseEntity<Personne> getPersonneById(@PathVariable Long id) {
        Optional<Personne> personne = personneService.findById(id);
        return personne.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // Update a Personne by ID
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PersonneDTO> updatePersonne(
            @PathVariable Long id,
            @RequestPart("personne") String personneJson,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {

        logger.info("Received PUT request to update personne with ID: {}", id);

        try {
            // Parse the JSON string into a PersonneDTO object
            ObjectMapper objectMapper = new ObjectMapper();
            PersonneDTO personneDTO = objectMapper.readValue(personneJson, PersonneDTO.class);
            Genre.valueOf(personneDTO.getGenre());
            // Log the parsed data
            logger.info("Updating personne details: Email: {}", personneDTO.getEmail());

            // Update the personne
            PersonneDTO updatedPersonne = personneService.updatePersonne(id, personneDTO, profileImage);

            // Log success
            logger.info("Personne with ID: {} successfully updated.", id);

            // Return successful response
            return new ResponseEntity<>(updatedPersonne, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            // Handle JSON parsing errors
            logger.error("Error parsing personne JSON: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (MultipartException e) {
            // Handle multipart/form-data specific exceptions
            logger.error("Multipart error occurred while uploading profile image: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (EntityNotFoundException e) {
            // Handle case where personne is not found
            logger.error("Personne not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ValidationException e) {
            // Handle validation errors
            logger.error("Validation error for personne update: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            // Handle general exceptions
            logger.error("An error occurred while updating personne: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/count-users")
    public ResponseEntity<Long> countUsers() {
        long userCount = personneService.countUsers();
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }

    // Count coaches
    @GetMapping("/count-coaches")
    public ResponseEntity<Long> countCoaches() {
        long coachCount = personneService.countCoaches();
        return new ResponseEntity<>(coachCount, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Personne>> getUsers() {
        List<Personne> users = personneService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/coaches")
    public ResponseEntity<List<Personne>> getCoaches() {
        List<Personne> coaches = personneService.getCoaches();
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }



}

