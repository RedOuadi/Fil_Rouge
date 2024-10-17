package com.fitness_management.services;

import com.fitness_management.dto.ProgrammeEntrainementDTO;
import com.fitness_management.exception.ResourceNotFoundException;
import com.fitness_management.mapper.ProgrammeEntrainementMapper;
import com.fitness_management.models.Coach;
import com.fitness_management.models.ProgrammeEntrainement;
import com.fitness_management.repositories.CoachRepository;
import com.fitness_management.repositories.ProgrammeEntrainementRepository;
import com.fitness_management.services.interfaces.ProgrammeEntrainementServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgrammeEntrainementService implements ProgrammeEntrainementServiceI {

    private final ProgrammeEntrainementRepository programmeRepository;
    private final CoachRepository coachRepository;
    private final ProgrammeEntrainementMapper programmeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProgrammeEntrainementDTO> getAllProgrammes() {
        return programmeRepository.findAll().stream()
                .map(programmeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgrammeEntrainementDTO> getProgrammesByCoachId(Long coachId) {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(() -> new ResourceNotFoundException("Coach", "id", coachId.toString()));

        return programmeRepository.findByCoach(coach).stream()
                .map(programmeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProgrammeEntrainementDTO getProgrammeById(Long id) {
        ProgrammeEntrainement programme = programmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programme", "id", id.toString()));
        return programmeMapper.toDTO(programme);
    }

    @Override
    @Transactional
    public ProgrammeEntrainementDTO createProgramme(ProgrammeEntrainementDTO dto) {
        Coach coach = coachRepository.findById(dto.getCoachId())
                .orElseThrow(() -> new ResourceNotFoundException("Coach", "id", dto.getCoachId().toString()));

        ProgrammeEntrainement programme = programmeMapper.toEntity(dto, coach);
        programme = programmeRepository.save(programme);
        return programmeMapper.toDTO(programme);
    }

    @Override
    @Transactional
    public ProgrammeEntrainementDTO updateProgramme(Long id, ProgrammeEntrainementDTO dto) {
        ProgrammeEntrainement existingProgramme = programmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programme", "id", id.toString()));

        Coach coach = coachRepository.findById(dto.getCoachId())
                .orElseThrow(() -> new ResourceNotFoundException("Coach", "id", dto.getCoachId().toString()));

        existingProgramme.setNom(dto.getNom());
        existingProgramme.setDescription(dto.getDescription());
        existingProgramme.setCoach(coach);

        existingProgramme = programmeRepository.save(existingProgramme);
        return programmeMapper.toDTO(existingProgramme);
    }

    @Override
    @Transactional
    public void deleteProgramme(Long id) {
        if (!programmeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Programme", "id", id.toString());
        }
        programmeRepository.deleteById(id);
    }
}
