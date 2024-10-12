package com.fitness_management.mapper;

import com.fitness_management.dto.PersonneDTO;
import com.fitness_management.dto.ImageDTO;
import com.fitness_management.models.*;
import org.springframework.stereotype.Component;

@Component
public class PersonneMapper {

    public PersonneDTO toDTO(Personne personne) {
        if (personne == null) {
            return null;
        }

        PersonneDTO dto = new PersonneDTO();
        dto.setId(personne.getId());
        dto.setNom(personne.getNom());
        dto.setPrenom(personne.getPrenom());  // Adjusted to match the DTO
        dto.setEmail(personne.getEmail());
        dto.setUsername(personne.getUsername());
        dto.setRole(personne.getRole().name());
        if (dto.getGenre() != null) {
            personne.setGenre(Genre.valueOf(dto.getGenre().toUpperCase()));
        } else {
            personne.setGenre(null);
        }


        if (personne.getProfileImage() != null) {
            dto.setProfileImage(toImageDto(personne.getProfileImage()));
        }

        return dto;
    }

    public Personne toEntity(PersonneDTO dto) {
        if (dto == null) {
            return null;
        }

        Personne personne;
        Role role = Role.valueOf(dto.getRole());  // This will now match the enum
        switch (role) {
            case ROLE_UTILISATEUR:
                personne = new User();
                break;
            case ROLE_ADMIN:
                personne = new Admin();
                break;
            case ROLE_COACH:
                personne = new Coach();
                break;
            default:
                throw new IllegalArgumentException("Unknown role: " + dto.getRole());
        }

        personne.setId(dto.getId());
        personne.setNom(dto.getNom());
        personne.setPrenom(dto.getPrenom());
        personne.setEmail(dto.getEmail());
        personne.setUsername(dto.getUsername());
        personne.setMotDePasse(dto.getMotDePasse());
        personne.setRole(Role.valueOf(dto.getRole().toUpperCase()));
        personne.setGenre(Genre.valueOf(dto.getGenre().toUpperCase()));

        if (dto.getProfileImage() != null) {
            personne.setProfileImage(toImageEntity(dto.getProfileImage()));
        }

        return personne;
    }

    public ImageDTO toImageDto(Image image) {
        if (image == null) {
            return null;
        }

        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setImageUrl(image.getImageUrl());
        dto.setCloudinaryImageId(image.getCloudinaryImageId());
        return dto;
    }

    public Image toImageEntity(ImageDTO dto) {
        if (dto == null) {
            return null;
        }

        Image image = new Image();
        image.setId(dto.getId());
        image.setImageUrl(dto.getImageUrl());
        image.setCloudinaryImageId(dto.getCloudinaryImageId());
        return image;
    }
}
