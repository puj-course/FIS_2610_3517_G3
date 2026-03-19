package com.norafit.norafit.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.norafit.norafit.entities.Exercises;
import com.norafit.norafit.repositories.ExerciseRepository;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Exercises saveExercise(Exercises exercise) {
        if (exercise == null) {
            throw new IllegalArgumentException("El ejercicio no puede ser null.");
        }

        if (exercise.getExerciseName() == null || exercise.getExerciseName().isBlank()) {
            throw new IllegalArgumentException("El nombre del ejercicio es obligatorio.");
        }

        if (exercise.getCreatedAt() == null) {
            exercise.setCreatedAt(LocalDateTime.now());
        }

        return exerciseRepository.save(exercise);
    }

    public List<Exercises> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public Exercises getExerciseById(Integer id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ejercicio no encontrado."));
    }

    public List<Exercises> getExercisesByRoutineId(Integer routineId) {
        return exerciseRepository.findByRoutineId(routineId);
    }

    public Exercises updateExercise(Integer id, Exercises updatedExercise) {
        Exercises existing = exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ejercicio no encontrado."));

        existing.setExerciseName(updatedExercise.getExerciseName());
        existing.setDescription(updatedExercise.getDescription());
        existing.setDurationSeconds(updatedExercise.getDurationSeconds());
        existing.setDefaultRestSeconds(updatedExercise.getDefaultRestSeconds());
        existing.setOrdering(updatedExercise.getOrdering());
        existing.setRoutineId(updatedExercise.getRoutineId());

        return exerciseRepository.save(existing);
    }

    public void deleteExercise(Integer id) {
        if (!exerciseRepository.existsById(id)) {
            throw new IllegalArgumentException("Ejercicio no encontrado.");
        }

        exerciseRepository.deleteById(id);
    }
}
