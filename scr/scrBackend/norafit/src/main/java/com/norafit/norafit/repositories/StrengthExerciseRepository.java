package com.norafit.norafit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.norafit.norafit.entities.StrengthExercise;

public interface StrengthExerciseRepository extends JpaRepository<StrengthExercise, Long> {
}