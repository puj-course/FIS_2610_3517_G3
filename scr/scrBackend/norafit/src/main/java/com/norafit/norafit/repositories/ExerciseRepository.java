package com.norafit.norafit.repositories;

import com.norafit.norafit.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    // No se necesita escribir métodos aquí, JpaRepository tiene todos los métodos necesarios: .save(), .findById(), etc.
}
