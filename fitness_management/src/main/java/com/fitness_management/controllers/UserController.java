package com.fitness_management.controllers;


import com.fitness_management.dto.PersonneDTO;
import com.fitness_management.models.Personne;
import com.fitness_management.models.User;
import com.fitness_management.services.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private PersonneService personneService;

    // Récupérer tous les utilisateurs
    @GetMapping
    public List<PersonneDTO> getAllUsers() {
        List<Personne> users = personneService.getUsers();
        return users.stream()
                .map(personne -> personneService.getPersonneMapper().toDTO(personne))
                .toList();
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<PersonneDTO> getUserById(@PathVariable Long id) {
        return personneService.findById(id)
                .map(personne -> ResponseEntity.ok(personneService.getPersonneMapper().toDTO(personne)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un nouvel utilisateur
    @PostMapping
    public ResponseEntity<PersonneDTO> createUser(@RequestBody PersonneDTO personneDTO) {
        PersonneDTO savedUser = personneService.registerPersonne(personneDTO, null);
        return ResponseEntity.ok(savedUser);
    }

    // Mettre à jour un utilisateur existant
    @PutMapping("/{id}")
    public ResponseEntity<PersonneDTO> updateUser(@PathVariable Long id, @RequestBody PersonneDTO personneDTO) {
        Personne updatedUser = personneService.updatePersonne(id, personneService.getPersonneMapper().toEntity(personneDTO));
        return ResponseEntity.ok(personneService.getPersonneMapper().toDTO(updatedUser));
    }

    // Supprimer un utilisateur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return ResponseEntity.noContent().build();
    }
}
