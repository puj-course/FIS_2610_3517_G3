package com.norafit.norafit.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.HIITCardio;
import com.norafit.norafit.entities.Routine;

@Service
public class HIITExecutionService {

    public List<String> executeRoutine(Routine routine, boolean realTime) {
        if (routine == null) {
            throw new IllegalArgumentException("La rutina es obligatoria.");
        }

        List<HIITCardio> hiitExercises = routine.getExercises().stream()
                .filter(HIITCardio.class::isInstance)
                .map(HIITCardio.class::cast)
                .toList();

        if (hiitExercises.isEmpty()) {
            throw new IllegalArgumentException("La rutina seleccionada no contiene ejercicios HIIT.");
        }

        List<String> events = new ArrayList<>();
        events.add("=== INICIO DE RUTINA HIIT: " + routine.getRoutineName() + " ===");

        int totalExercises = hiitExercises.size();
        for (int i = 0; i < totalExercises; i++) {
            HIITCardio hiit = hiitExercises.get(i);
            events.addAll(executeExercise(hiit, i + 1, totalExercises, realTime));
        }

        events.add("=== FIN DE RUTINA HIIT ===");
        return events;
    }

    private List<String> executeExercise(HIITCardio hiit, int exerciseNumber, int totalExercises, boolean realTime) {
        validateHIITConfiguration(hiit);

        List<String> events = new ArrayList<>();
        events.add("Ejercicio " + exerciseNumber + "/" + totalExercises + ": " + hiit.getExerciseName());
        events.add("Configuración -> rondas: " + hiit.getRounds()
                + ", trabajo: " + hiit.getWorkTimeSeconds() + "s"
                + ", descanso: " + hiit.getRestTimeSeconds() + "s");

        for (int round = 1; round <= hiit.getRounds(); round++) {
            events.add("Ronda " + round + " de " + hiit.getRounds());
            countdown("TRABAJO", hiit.getWorkTimeSeconds(), realTime, events);

            if (round < hiit.getRounds() && hiit.getRestTimeSeconds() > 0) {
                countdown("DESCANSO", hiit.getRestTimeSeconds(), realTime, events);
            }
        }

        events.add("Ejercicio HIIT finalizado: " + hiit.getExerciseName());
        return events;
    }

    private void validateHIITConfiguration(HIITCardio hiit) {
        if (hiit == null) {
            throw new IllegalArgumentException("El ejercicio HIIT es obligatorio.");
        }
        if (hiit.getRounds() <= 0) {
            throw new IllegalArgumentException("Las rondas del HIIT deben ser mayores a 0.");
        }
        if (hiit.getWorkTimeSeconds() <= 0) {
            throw new IllegalArgumentException("El tiempo de trabajo debe ser mayor a 0.");
        }
        if (hiit.getRestTimeSeconds() < 0) {
            throw new IllegalArgumentException("El tiempo de descanso no puede ser negativo.");
        }
    }

    private void countdown(String state, int seconds, boolean realTime, List<String> events) {
        for (int remaining = seconds; remaining >= 1; remaining--) {
            events.add(state + " - " + remaining + "s restantes");
            if (realTime) {
                sleepOneSecond();
            }
        }
    }

    private void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("La ejecución HIIT fue interrumpida.", e);
        }
    }

    public boolean routineHasHIIT(Routine routine) {
        if (routine == null || routine.getExercises() == null) {
            return false;
        }

        for (Exercise exercise : routine.getExercises()) {
            if (exercise instanceof HIITCardio) {
                return true;
            }
        }
        return false;
    }

    public List<String> executeSingleExercise(HIITCardio hiit, boolean realTime) {
    if (hiit == null) {
        throw new IllegalArgumentException("El ejercicio HIIT es obligatorio.");
    }

    List<String> events = new ArrayList<>();
    events.add("=== INICIO DE EJERCICIO HIIT ===");
    events.addAll(executeExercise(hiit, 1, 1, realTime));
    events.add("=== FIN DE EJERCICIO HIIT ===");

    return events;
}
}
