package com.fitness_management.repositories;

import com.fitness_management.models.Coach;
import com.fitness_management.models.ProgrammeEntrainement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgrammeEntrainementRepository extends JpaRepository<ProgrammeEntrainement, Long> {

    List<ProgrammeEntrainement> findByCoach(Coach coach);
}
