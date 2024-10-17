package com.fitness_management.services.interfaces;

import com.fitness_management.dto.ObjectifFitnessDTO;
import java.util.List;

public interface ObjectifFitnessServiceI {
    ObjectifFitnessDTO createObjectif(ObjectifFitnessDTO objectifFitnessDTO);
    List<ObjectifFitnessDTO> getAllObjectifs();
    ObjectifFitnessDTO getObjectifById(Long id);
    ObjectifFitnessDTO updateObjectif(Long id, ObjectifFitnessDTO objectifFitnessDTO);
    void deleteObjectif(Long id);
    List<ObjectifFitnessDTO> getObjectifsByUtilisateurId(Long utilisateurId);
}
