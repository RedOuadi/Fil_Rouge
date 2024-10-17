package com.fitness_management.services.interfaces;

import com.fitness_management.dto.PersonneDTO;
import com.fitness_management.models.Personne;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

public interface PersonneServiceI {
    PersonneDTO registerPersonne(PersonneDTO personneDTO, MultipartFile profileImage);
    Optional<Personne> authenticatePersonne(String email, String motDePasse);
    String getRole(Personne personne);
    Optional<Personne> findByEmail(String email);
    List<Personne> findAll();
    void deletePersonne(Long id);
    void createAdminUserIfNotExist();
    Optional<Personne> findById(Long id);
    PersonneDTO updatePersonne(Long id, PersonneDTO updatedPersonneDTO, MultipartFile profileImage);
    long countUsers();
    long countCoaches();
    List<Personne> getUsers();
    List<Personne> getCoaches();
}
