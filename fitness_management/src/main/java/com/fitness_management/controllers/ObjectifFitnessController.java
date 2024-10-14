package com.fitness_management.controllers;

import com.fitness_management.dto.ObjectifFitnessDTO;
import com.fitness_management.services.ObjectifFitnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objectifs")
public class ObjectifFitnessController {

    @Autowired
    private ObjectifFitnessService objectifFitnessService;

    @PostMapping
    public ResponseEntity<ObjectifFitnessDTO> createObjectif(@RequestBody ObjectifFitnessDTO objectifFitnessDTO) {
        ObjectifFitnessDTO createdObjectif = objectifFitnessService.createObjectif(objectifFitnessDTO);
        return new ResponseEntity<>(createdObjectif, HttpStatus.CREATED);
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<ObjectifFitnessDTO>> getObjectifsByUtilisateurId(@PathVariable Long utilisateurId) {
        List<ObjectifFitnessDTO> objectifs = objectifFitnessService.getObjectifsByUtilisateurId(utilisateurId);
        return new ResponseEntity<>(objectifs, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ObjectifFitnessDTO>> getAllObjectifs() {
        List<ObjectifFitnessDTO> objectifs = objectifFitnessService.getAllObjectifs();
        return new ResponseEntity<>(objectifs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectifFitnessDTO> getObjectifById(@PathVariable Long id) {
        ObjectifFitnessDTO objectif = objectifFitnessService.getObjectifById(id);
        return new ResponseEntity<>(objectif, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectifFitnessDTO> updateObjectif(@PathVariable Long id, @RequestBody ObjectifFitnessDTO objectifFitnessDTO) {
        ObjectifFitnessDTO updatedObjectif = objectifFitnessService.updateObjectif(id, objectifFitnessDTO);
        return new ResponseEntity<>(updatedObjectif, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObjectif(@PathVariable Long id) {
        objectifFitnessService.deleteObjectif(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

