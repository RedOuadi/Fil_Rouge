package com.fitness_management.repositories;

import com.fitness_management.models.Exercice;
import com.fitness_management.models.ObjectifFitness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectifFitnessRepository extends JpaRepository<ObjectifFitness, Long> {
}
