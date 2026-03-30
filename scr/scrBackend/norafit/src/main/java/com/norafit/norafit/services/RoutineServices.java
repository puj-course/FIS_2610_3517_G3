package com.norafit.norafit.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.norafit.norafit.entities.Exercises;
import com.norafit.norafit.entities.Series;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.repositories.RoutineRepository;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.repositories.ExerciseRepository;

import jakarta.transaction.Transactional;

@Service
public class RoutineServices {
    
    private final RoutineRepository routineRepository;
    private final ExerciseRepository exerciseRepository;

    public RoutineServices(RoutineRepository routineRepository, ExerciseRepository exerciseRepository) {
    this.routineRepository = routineRepository;
    this.exerciseRepository = exerciseRepository;
}

    public Routine create(String routineName, User user) {
        if (routineName == null || routineName.isBlank()) {
            throw new IllegalArgumentException("El nombre de la rutina es obligatorio.");
        }
        if (user == null) {
            throw new IllegalArgumentException("La rutina debe tener un usuario.");
        }

        Routine routine = new Routine();
        routine.setRoutineName(routineName.trim());
        routine.setCreatedAt(LocalDate.now());
        routine.setExercises(new ArrayList<>());
        routine.setTotalTimeSeconds(0f);
        routine.setUser(user);

        return routineRepository.save(routine);
    }

    public Routine getRoutineWithExercises(Integer routineId) {
    if (routineId == null) {
        throw new IllegalArgumentException("El id de la rutina no puede ser null.");
    }

    Routine routine = routineRepository.findById(routineId).orElseThrow(() -> new IllegalArgumentException("Rutina no encontrada."));

    List<Exercises> exercises = exerciseRepository.findByRoutineId(routineId);
    routine.setExercises(exercises);

    return routine;
}

    //MÉTODO NUEVO
    @Transactional
    public void rename(Integer id, String newName) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser null.");
        }
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        routineRepository.renameRoutine(id, newName.trim());
    }

    public Exercises getExercise(Routine routine, int exerciseId) {
        if (routine == null) throw new IllegalArgumentException("Routine no puede ser null.");

        List<Exercises> exercises = routine.getExercises();
        if (exercises == null) return null;

        for (Exercises ex : exercises) {
            if (ex != null && ex.getExerciseId() == exerciseId) {
                return ex;
            }
        }
        return null;
    }

    public void addExercise(Routine routine, Exercises exercise) {
        if (routine == null) throw new IllegalArgumentException("Routine no puede ser null.");
        if (exercise == null) throw new IllegalArgumentException("Exercise no puede ser null.");

        if (routine.getExercises() == null) {
            routine.setExercises(new ArrayList<>());
        }

        routine.getExercises().add(exercise);
        routine.setTotalTimeSeconds(calculateTotalTimeSeconds(routine));
    }

    public float calculateTotalTimeSeconds(Routine routine) {
        if (routine == null) {
            throw new IllegalArgumentException("Routine no puede ser null.");
        }

        if (routine.getExercises() == null || routine.getExercises().isEmpty()) {
            return 0f;
        }

        int total = 0;

        for (Exercises ex : routine.getExercises()) {
            if (ex == null || ex.getSeries() == null) continue;

            for (Series s : ex.getSeries()) {
                if (s == null) continue;
                total += Math.max(0, s.getDurationSeconds());
                total += Math.max(0, s.getRestTimeSeconds());
            }
        }

        return (float) total;
    }
}
