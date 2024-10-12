package com.fitness_management.controllers;

import com.fitness_management.dto.ProgrammeEntrainementDTO;
import com.fitness_management.services.ProgrammeEntrainementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programmes")
@RequiredArgsConstructor
public class ProgrammeEntrainementController {

    private final ProgrammeEntrainementService programmeService;

    @GetMapping
    public ResponseEntity<List<ProgrammeEntrainementDTO>> getAllProgrammes() {
        return ResponseEntity.ok(programmeService.getAllProgrammes());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProgrammeEntrainementDTO> getProgrammeById(@PathVariable Long id) {
        ProgrammeEntrainementDTO programmeDTO = programmeService.getProgrammeById(id);
        return new ResponseEntity<>(programmeDTO, HttpStatus.OK);
    }

    @GetMapping("/coach/{coachId}")
    public List<ProgrammeEntrainementDTO> getProgrammesByCoachId(@PathVariable Long coachId) {
        return programmeService.getProgrammesByCoachId(coachId);
    }

    @PostMapping
    public ResponseEntity<ProgrammeEntrainementDTO> createProgramme(@RequestBody ProgrammeEntrainementDTO programmeDTO) {
        return new ResponseEntity<>(programmeService.createProgramme(programmeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")

    public ResponseEntity<ProgrammeEntrainementDTO> updateProgramme(
            @PathVariable Long id,
            @RequestBody ProgrammeEntrainementDTO programmeDTO) {
        return ResponseEntity.ok(programmeService.updateProgramme(id, programmeDTO));
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteProgramme(@PathVariable Long id) {
        programmeService.deleteProgramme(id);
        return ResponseEntity.noContent().build();
    }
}