package com.fitness_management.controllers;

import com.fitness_management.dto.PersonneDTO;
import com.fitness_management.models.Coach;
import com.fitness_management.models.Personne;
import com.fitness_management.services.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    @Autowired
    private PersonneService personneService;

    // Récupérer tous les coaches
    @GetMapping
    public List<PersonneDTO> getAllCoaches() {
        List<Personne> coaches = personneService.getCoaches();
        return coaches.stream()
                .map(personne -> personneService.getPersonneMapper().toDTO(personne))
                .toList();
    }

    // Récupérer un coach par ID
    @GetMapping("/{id}")
    public ResponseEntity<PersonneDTO> getCoachById(@PathVariable Long id) {
        return personneService.findById(id)
                .map(personne -> ResponseEntity.ok(personneService.getPersonneMapper().toDTO(personne)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un nouveau coach
    @PostMapping
    public ResponseEntity<PersonneDTO> createCoach(@RequestBody PersonneDTO personneDTO) {
        PersonneDTO savedCoach = personneService.registerPersonne(personneDTO, null);
        return ResponseEntity.ok(savedCoach);
    }

    // Mettre à jour un coach existant
    @PutMapping("/{id}")
    public ResponseEntity<PersonneDTO> updateCoach(@PathVariable Long id, @RequestBody PersonneDTO personneDTO) {
        Personne updatedCoach = personneService.updatePersonne(id, personneService.getPersonneMapper().toEntity(personneDTO));
        return ResponseEntity.ok(personneService.getPersonneMapper().toDTO(updatedCoach));
    }

    // Supprimer un coach par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoach(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return ResponseEntity.noContent().build();
    }
}

