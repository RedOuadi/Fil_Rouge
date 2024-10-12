package com.fitness_management.repositories;

import com.fitness_management.models.Coach;
import com.fitness_management.models.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface CoachRepository    extends JpaRepository<Coach, Long> {
}
