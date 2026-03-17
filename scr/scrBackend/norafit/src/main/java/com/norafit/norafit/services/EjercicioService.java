package com.norafit.norafit.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.norafit.norafit.entities.Exercises;
import com.norafit.norafit.entities.Routine;

@Service
public class EjercicioService {

    public Exercises create(String exerciseName, String description) {

        if (exerciseName == null || exerciseName.isBlank()) {
            throw new IllegalArgumentException("El nombre del ejercicio es obligatorio.");
        }

        Exercises exercise = new Exercises();
        exercise.setExerciseName(exerciseName.trim());
        exercise.setDescription(description);
        exercise.setSeries(new ArrayList<>());

        return exercise;
    }

    public void addExerciseToRoutine(Routine routine, Exercises exercise) {

        if (routine == null) {
            throw new IllegalArgumentException("la rutina debe existir.");
        }

        if (exercise == null) {
            throw new IllegalArgumentException("el ejercicio debe existir.");
        }

        if (routine.getExercises() == null) {
            routine.setExercises(new ArrayList<>());
        }

        routine.getExercises().add(exercise);
    }

    public Exercises getExercise(Routine routine, int exerciseId) {

        if (routine == null) {
            throw new IllegalArgumentException("la rutina debe existir.");
        }

        List<Exercises> exercises = routine.getExercises();
        if (exercises == null) return null;

        for (Exercises ex : exercises) {
            if (ex != null && ex.getExerciseId() == exerciseId) {
                return ex;
            }
        }

        return null;
    }
}
