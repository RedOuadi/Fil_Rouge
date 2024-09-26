package com.fitness_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonneDTO {
    private Long id;
    private String nom;
    private String prenom;  // Changed to camelCase
    private String email;
    private String motDePasse;  // If needed, consider omitting this in DTO
    private String username;
    private String genre;  // Adjusted for camelCase
    private String role;
    private ImageDTO profileImage;



    public void setRole(String role) {
        this.role = role.toUpperCase().startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase();
    }
}

