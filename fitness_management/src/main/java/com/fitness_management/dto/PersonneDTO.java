package com.fitness_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonneDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String username;
    private String role;
}
