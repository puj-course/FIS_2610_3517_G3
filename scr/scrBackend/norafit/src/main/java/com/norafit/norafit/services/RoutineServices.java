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

  public Routine createRoutine(String name, User user) { 
        if (name == null || name.isBlank()) { 
            throw new IllegalArgumentException("El nombre de la rutina no puede estar vacío."); 
        } 
        Routine newRoutine = new Routine(); 
        newRoutine.setRoutineName(name); 
        newRoutine.setUser(user);  
        newRoutine.setCreatedAt(LocalDate.now()); 
        newRoutine.setTotalTimeSeconds(0); 

        return routineRepository.save(newRoutine); 
    } 

  @Transactional
    public void removeRoutine(Long routineId, User user) {
    // 1. se busca la rutina primero
    Routine routine = routineRepository.findById(routineId)
        .orElseThrow(() -> new IllegalArgumentException("La rutina con ID " + routineId + " no existe."));

    // 2. se verifica que la rutina pertenezca al usuario (Seguridad)
    if (routine.getUser().getId() != user.getId()) {
    throw new IllegalArgumentException("No tienes permiso para eliminar esta rutina.");
    }
}

