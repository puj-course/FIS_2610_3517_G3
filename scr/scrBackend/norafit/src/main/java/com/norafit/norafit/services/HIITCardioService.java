package com.norafit.norafit.services;

import org.springframework.stereotype.Service;

import com.norafit.norafit.entities.Exercises;

@Service
public class HIITCardioService {
    public Exercises createHIIT(
            String name,
            String description,
            String intensity,
            Integer rounds,
            Integer workTime,
            Integer restTime
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        if (rounds == null || workTime == null || restTime == null) {
            throw new IllegalArgumentException("Los tiempos y rondas no pueden ser null.");
        }

        Exercises hiit = new Exercises();
        hiit.setExerciseName(name.trim());

        hiit.setDescription(
                description +
                " | Intensidad: " + intensity +
                " | Rondas: " + rounds +
                " | Trabajo: " + workTime + "s" +
                " | Descanso: " + restTime + "s"
        );

        hiit.setDurationSeconds((workTime + restTime) * rounds);

        return hiit;
    }

    /**
     * Actualiza la intensidad del HIIT.
     */
    public void setIntensity(Exercises exercise, String intensity) {
        if (exercise == null) {
            throw new IllegalArgumentException("Exercise no puede ser null.");
        }

        String currentDescription = exercise.getDescription() == null
                ? ""
                : exercise.getDescription();

        exercise.setDescription(currentDescription + " | Intensidad: " + intensity);
    }
}
