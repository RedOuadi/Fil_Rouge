package com.fitness_management.services.interfaces;

import com.fitness_management.dto.ActiviteDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ActiviteServiceI {

    ActiviteDTO createActivite(ActiviteDTO activiteDTO, MultipartFile imageFile, MultipartFile videoFile);

    List<ActiviteDTO> getAllActivites();

    Optional<ActiviteDTO> getActiviteById(Long id);

    List<ActiviteDTO> getActivitesByUtilisateurId(Long utilisateurId);

    ActiviteDTO updateActivite(Long id, ActiviteDTO activiteDTO, MultipartFile imageFile, MultipartFile videoFile);

    void deleteActivite(Long id);
}
