package com.fitness_management.services.interfaces;

import com.fitness_management.dto.ProgrammeEntrainementDTO;
import java.util.List;

public interface ProgrammeEntrainementServiceI {

    List<ProgrammeEntrainementDTO> getAllProgrammes();

    List<ProgrammeEntrainementDTO> getProgrammesByCoachId(Long coachId);

    ProgrammeEntrainementDTO getProgrammeById(Long id);

    ProgrammeEntrainementDTO createProgramme(ProgrammeEntrainementDTO dto);

    ProgrammeEntrainementDTO updateProgramme(Long id, ProgrammeEntrainementDTO dto);

    void deleteProgramme(Long id);
}
