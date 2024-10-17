package com.fitness_management.services;


import com.fitness_management.dto.PersonneDTO;
import com.fitness_management.mapper.PersonneMapper;
import com.fitness_management.models.*;
import com.fitness_management.repositories.PersonneRepository;
import com.fitness_management.security.JwtAuth;
import com.fitness_management.services.interfaces.PersonneServiceI;
import com.fitness_management.util.FileUploadUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;



@Service
public class PersonneService  implements PersonneServiceI {

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuth jwtAuth;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Getter

    @Autowired
    private PersonneMapper personneMapper;
    private static final Logger logger = LoggerFactory.getLogger(PersonneService.class);



    @Override
    @Transactional
    public PersonneDTO registerPersonne(PersonneDTO personneDTO, MultipartFile profileImage) {
        logger.info("Registering new personne: {}", personneDTO.getEmail());


        Personne personne = personneMapper.toEntity(personneDTO);
        personne.setMotDePasse(passwordEncoder.encode(personne.getMotDePasse()));

        // Assign roles based on instance type
        if (personne instanceof User) {
            ((User) personne).setRole(Role.ROLE_UTILISATEUR);
        } else if (personne instanceof Admin) {
            ((Admin) personne).setRole(Role.ROLE_ADMIN);
        } else if (personne instanceof Coach) {
            ((Coach) personne).setRole(Role.ROLE_COACH);
        }
        FileUploadUtil.assertAllowed(profileImage, FileUploadUtil.IMAGE_PATTERN);

        // Extract the base name and extension from the original file name
        String originalFileName = profileImage.getOriginalFilename();
        String baseName = originalFileName != null ? originalFileName.substring(0, originalFileName.lastIndexOf('.')) : "profile";
        String extension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf('.') + 1) : "png";

        String fileName = FileUploadUtil.getFileName(baseName, extension);


        if (personne instanceof User) {
            ((User) personne).setRole(Role.ROLE_UTILISATEUR);
        } else if (personne instanceof Admin) {
            ((Admin) personne).setRole(Role.ROLE_ADMIN);
        } else if (personne instanceof Coach) {
            ((Coach) personne).setRole(Role.ROLE_COACH);
        }

        if (profileImage != null && !profileImage.isEmpty()) {
            CloudinaryResponse response = cloudinaryService.uploadFile(profileImage,fileName, "image");
            Image image = new Image();
            image.setImageUrl(response.getUrl());
            image.setCloudinaryImageId(response.getPublicId());
            personne.setProfileImage(image);
        }

        // Save the personne
        Personne savedPersonne = personneRepository.save(personne);
        logger.info("Personne saved with ID: {}", savedPersonne.getId());

        return personneMapper.toDTO(savedPersonne);
    }

    @Override
    public Optional<Personne> authenticatePersonne(String email, String motDePasse) {
        Optional<Personne> personne = personneRepository.findByEmail(email);

        if (personne.isPresent() && passwordEncoder.matches(motDePasse, personne.get().getMotDePasse())) {
            return personne;  // Return the authenticated person
        }

        return Optional.empty();
    }

    public String getRole(Personne personne) {
        // Assuming that the role is stored in the Personne entity or its subclasses
        return personne.getRole().toString();
    }

    @Override
    public Optional<Personne> findByEmail(String email) {
        return personneRepository.findByEmail(email);
    }
    @Override
    public List<Personne> findAll() {
        return personneRepository.findAll();
    }
    @Override
    public void deletePersonne(Long id) {
        personneRepository.deleteById(id);
    }


    @Override
    public void createAdminUserIfNotExist() {
        String adminEmail = "admin@example.com";
        Optional<Personne> existingAdmin = personneRepository.findByEmail(adminEmail);

        if (existingAdmin.isEmpty()) {
            Admin admin = new Admin();
            admin.setEmail(adminEmail);
            admin.setMotDePasse(passwordEncoder.encode("admin"));
            personneRepository.save(admin);
        }
    }
    @Override
    public Optional<Personne> findById(Long id) {
        return personneRepository.findById(id);
    }


    @Override
    @Transactional
    public PersonneDTO updatePersonne(Long id, PersonneDTO updatedPersonneDTO, MultipartFile profileImage) {
        logger.info("Updating personne with ID: {}", id);

        Personne existingPersonne = personneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personne not found with id: " + id));

        // Update fields
        if (updatedPersonneDTO.getNom() != null) {
            existingPersonne.setNom(updatedPersonneDTO.getNom());
        }
        if (updatedPersonneDTO.getPrenom() != null) {
            existingPersonne.setPrenom(updatedPersonneDTO.getPrenom());
        }
        if (updatedPersonneDTO.getEmail() != null) {
            existingPersonne.setEmail(updatedPersonneDTO.getEmail());
        }
        if (updatedPersonneDTO.getUsername() != null) {
            existingPersonne.setUsername(updatedPersonneDTO.getUsername());
        }
        if (updatedPersonneDTO.getGenre() != null) {
            existingPersonne.setGenre(Genre.valueOf(updatedPersonneDTO.getGenre()));
        }

        // Handle password update if provided
        if (updatedPersonneDTO.getMotDePasse() != null && !updatedPersonneDTO.getMotDePasse().isEmpty()) {
            existingPersonne.setMotDePasse(passwordEncoder.encode(updatedPersonneDTO.getMotDePasse()));
        }

        // Handle profile image update if provided
        if (profileImage != null && !profileImage.isEmpty()) {
            FileUploadUtil.assertAllowed(profileImage, FileUploadUtil.IMAGE_PATTERN);

            // Delete existing image if present


            String originalFileName = profileImage.getOriginalFilename();
            String baseName = originalFileName != null ?
                    originalFileName.substring(0, originalFileName.lastIndexOf('.')) : "profile";
            String extension = originalFileName != null ?
                    originalFileName.substring(originalFileName.lastIndexOf('.') + 1) : "png";

            String fileName = FileUploadUtil.getFileName(baseName, extension);

            CloudinaryResponse response = cloudinaryService.uploadFile(profileImage, fileName, "image");

            Image image = existingPersonne.getProfileImage() != null ?
                    existingPersonne.getProfileImage() : new Image();
            image.setImageUrl(response.getUrl());
            image.setCloudinaryImageId(response.getPublicId());
            existingPersonne.setProfileImage(image);
        }

        Personne savedPersonne = personneRepository.save(existingPersonne);
        logger.info("Successfully updated personne with ID: {}", id);

        return personneMapper.toDTO(savedPersonne);

    }
    @Override
    public long countUsers() {
        return personneRepository.countByRole(Role.ROLE_UTILISATEUR);
    }


    @Override
    public long countCoaches() {
        return personneRepository.countByRole(Role.ROLE_COACH);
    }


    @Override
    public List<Personne> getUsers() {
        return personneRepository.findAllByRole(Role.ROLE_UTILISATEUR);
    }


    @Override
    public List<Personne> getCoaches() {
        return personneRepository.findAllByRole(Role.ROLE_COACH);
    }

}
