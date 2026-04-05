package com.norafit.norafit.services;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.StrengthExercise;
import com.norafit.norafit.repositories.ExerciseRepository;
import com.norafit.norafit.repositories.RoutineRepository;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final RoutineRepository routineRepository;

    public ExerciseService(ExerciseRepository exerciseRepository, RoutineRepository routineRepository) {
        this.exerciseRepository = exerciseRepository;
        this.routineRepository = routineRepository;
    }
    
    @Transactional
    public Exercise addStrengthExercise(Long routineId, String name, String desc, boolean hasWeight) {
        // 1. Se busca la rutina donde queremos meter el ejercicio
        Routine routine = routineRepository.findById(routineId)
            .orElseThrow(() -> new RuntimeException("Error: La rutina con ID " + routineId + " no existe."));

        // 2. se crea el objeto de fuerza (clase hija)
        StrengthExercise sExercise = new StrengthExercise();
        sExercise.updateBaseInfo(name, desc);
        sExercise.setHasWeight(hasWeight);

        // 3. se vincula usando el método puesto en Routine.java
        // Esto conecta al hijo con el padre en memoria
        routine.addExercise(sExercise);

        // 4. Guardamos el ejercicio en la base de datos
        // JPA se encarga de llenar la tabla 'exercises' y 'strength_exercises'
        return exerciseRepository.save(sExercise);
    }

    @Transactional
    public Exercise renameExercise(Long exerciseId, String newName) {
        // 1. Se busca el ejercicio
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("No existe el ejercicio con ID " + exerciseId));

        // 2. LA LÓGICA DE VALIDACIÓN SE QUEDA AQUÍ
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("El nombre del ejercicio no puede estar vacío.");
        }

        // 3. se usa el setter estándar
        exercise.setExerciseName(newName);

        // 4. Persistir
        return exerciseRepository.save(exercise);
    }
}

