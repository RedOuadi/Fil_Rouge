package com.fitness_management.repositories;

import com.fitness_management.models.Exercice;
import com.fitness_management.models.ObjectifFitness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObjectifFitnessRepository extends JpaRepository<ObjectifFitness, Long> {
    List<ObjectifFitness> findByUtilisateurId(Long utilisateurId);
}
