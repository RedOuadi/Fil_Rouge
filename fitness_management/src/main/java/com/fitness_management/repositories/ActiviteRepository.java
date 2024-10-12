package com.fitness_management.repositories;

import com.fitness_management.models.Activite;
import com.fitness_management.models.Exercice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiviteRepository extends JpaRepository<Activite, Long> {
}
