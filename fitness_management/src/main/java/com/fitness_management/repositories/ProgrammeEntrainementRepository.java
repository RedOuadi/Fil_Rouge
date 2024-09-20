package com.fitness_management.repositories;

import com.fitness_management.models.ProgrammeEntrainement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammeEntrainementRepository extends JpaRepository<ProgrammeEntrainement, Long> {
}
