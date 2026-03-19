package com.norafit.norafit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.norafit.norafit.entities.Exercises;

public interface ExerciseRepository extends JpaRepository<Exercises, Integer> {
    List<Exercises> findByRoutineId(Integer routineId);
}
