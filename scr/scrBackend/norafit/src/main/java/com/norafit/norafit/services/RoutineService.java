package com.norafit.norafit.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.repositories.RoutineRepository;


@Service
public class RoutineService {

    private final RoutineRepository routineRepository;
    

    public RoutineService(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    // Aquí es donde el usuario "crea" la rutina
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
    
    public List<Routine> getRoutinesByUser(User user) {
        return routineRepository.findByUser_Id(Long.valueOf(user.getId())); 
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

    // 3. se elimina 
    routineRepository.delete(routine);
    }
    @Transactional
public Routine renameRoutine(Long routineId, String newName, User user) {
    if (newName == null || newName.isBlank()) {
        throw new IllegalArgumentException("El nuevo nombre no puede estar vacío.");
    }

    // 1. se busca la rutina
    Routine routine = routineRepository.findById(routineId)
        .orElseThrow(() -> new IllegalArgumentException("La rutina no existe."));

    // 2. Validación de seguridad (que sea del usuario logueado)
    if (routine.getUser().getId() != user.getId()) {
    throw new IllegalArgumentException("No tienes permiso para editar esta rutina.");
}

    // 3. Cambiamos el nombre y guardamos
    routine.setRoutineName(newName);
    return routineRepository.save(routine);
 }
    @Transactional(readOnly = true)
    public Routine getRoutineById(Long id) {
        Routine routine = routineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("La rutina con ID " + id + " no existe."));
    
    routine.getExercises().size(); 
    
    return routine;
}


}
    
