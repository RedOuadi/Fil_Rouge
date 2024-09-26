package com.fitness_management.services;

import com.fitness_management.dto.PersonneDTO;
import com.fitness_management.mapper.PersonneMapper;
import com.fitness_management.models.*;
import com.fitness_management.repositories.PersonneRepository;
import com.fitness_management.security.JwtAuth;
import com.fitness_management.util.FileUploadUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;



@Service
public class PersonneService  {

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuth jwtAuth;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private PersonneMapper personneMapper;
    private static final Logger logger = LoggerFactory.getLogger(PersonneService.class);

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


    public Optional<Personne> findByEmail(String email) {
        return personneRepository.findByEmail(email);
    }

    public List<Personne> findAll() {
        return personneRepository.findAll();
    }

    public void deletePersonne(Long id) {
        personneRepository.deleteById(id);
    }

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

    public Optional<Personne> findById(Long id) {
        return personneRepository.findById(id);
    }

    public Personne updatePersonne(Long id, Personne updatedPersonne) {
        Optional<Personne> optionalPersonne = findById(id);
        if (optionalPersonne.isPresent()) {
            Personne existingPersonne = optionalPersonne.get();

            // Update fields
            existingPersonne.setNom(updatedPersonne.getNom());
            existingPersonne.setPrenom(updatedPersonne.getPrenom());
            existingPersonne.setEmail(updatedPersonne.getEmail());
            if (updatedPersonne.getMotDePasse() != null) {
                existingPersonne.setMotDePasse(passwordEncoder.encode(updatedPersonne.getMotDePasse()));
            }

            // Specific updates for subclasses (User, Admin, Coach)
            if (existingPersonne instanceof User && updatedPersonne instanceof User) {
                ((User) existingPersonne).setNiveauFitness(((User) updatedPersonne).getNiveauFitness());
            } else if (existingPersonne instanceof Admin && updatedPersonne instanceof Admin) {
                // Admin-specific updates can be added here
            } else if (existingPersonne instanceof Coach && updatedPersonne instanceof Coach) {
                ((Coach) existingPersonne).setCertification(((Coach) updatedPersonne).getCertification());
            }

            return personneRepository.save(existingPersonne);
        }
        throw new EntityNotFoundException("Personne not found with id: " + id);
    }
    public long countUsers() {
        return personneRepository.countByRole(Role.ROLE_UTILISATEUR);
    }

    public long countCoaches() {
        return personneRepository.countByRole(Role.ROLE_COACH);
    }

    public List<Personne> getUsers() {
        return personneRepository.findAllByRole(Role.ROLE_UTILISATEUR);
    }

    public List<Personne> getCoaches() {
        return personneRepository.findAllByRole(Role.ROLE_COACH);
    }



}
