package com.fitness_management.mapper;

import com.fitness_management.dto.PersonneDTO;
import com.fitness_management.models.Personne;
import com.fitness_management.models.Role;
import org.springframework.stereotype.Component;

@Component
public class PersonneMapper {

    public PersonneDTO toDto(Personne personne) {
        if (personne == null) {
            return null;
        }

        PersonneDTO dto = new PersonneDTO();
        dto.setId(personne.getId());
        dto.setNom(personne.getNom());
        dto.setPrenom(personne.getPrenom());
        dto.setEmail(personne.getEmail());
        dto.setUsername(personne.getUsername());
        dto.setRole(personne.getRole().name());

        return dto;
    }

    public Personne toEntity(PersonneDTO dto, Personne personne) {
        if (dto == null) {
            return null;
        }

        personne.setId(dto.getId());
        personne.setNom(dto.getNom());
        personne.setPrenom(dto.getPrenom());
        personne.setEmail(dto.getEmail());
        personne.setUsername(dto.getUsername());
        personne.setRole(Role.valueOf(dto.getRole()));

        return personne;
    }
}
