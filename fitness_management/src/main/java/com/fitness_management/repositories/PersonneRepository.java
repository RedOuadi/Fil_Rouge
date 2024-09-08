package com.fitness_management.repositories;

import com.fitness_management.models.Personne;
import com.fitness_management.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonneRepository extends JpaRepository<Personne, Long> {

    Optional<Personne> findByEmail(String adminEmail);
    long countByRole(Role role);
}