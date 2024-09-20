package com.fitness_management.controllers;

import com.fitness_management.models.ProgrammeEntrainement;
import com.fitness_management.services.ProgrammeEntrainementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/programmes")
public class ProgrammeEntrainementController {

    @Autowired
    private ProgrammeEntrainementService programmeService;

    @GetMapping
    public List<ProgrammeEntrainement> getAllProgrammes() {
        return programmeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgrammeEntrainement> getProgrammeById(@PathVariable Long id) {
        Optional<ProgrammeEntrainement> programme = programmeService.findById(id);
        if (programme.isPresent()) {
            return ResponseEntity.ok(programme.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ProgrammeEntrainement createProgramme(@RequestBody ProgrammeEntrainement programme) {
        return programmeService.save(programme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgrammeEntrainement> updateProgramme(@PathVariable Long id, @RequestBody ProgrammeEntrainement updatedProgramme) {
        try {
            ProgrammeEntrainement updated = programmeService.update(id, updatedProgramme);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgramme(@PathVariable Long id) {
        programmeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
