package com.norafit.norafit.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.norafit.norafit.entities.Exercises;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.Series;
import com.norafit.norafit.entities.User;

@Service
public class RoutineService {

  
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

      
        routine.setUser(user);


        routine.setTotalTimeSeconds(0f);
        return routine;
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

  
    


