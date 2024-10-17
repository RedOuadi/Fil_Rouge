package com.fitness_management.repositories;

import com.fitness_management.models.Exercice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciceRepository  extends JpaRepository<Exercice, Long> {

    List<Exercice> findByProgrammeId(Long programmeId);

    @Query("select count(*) from Exercice" )
    int cont ();
}
