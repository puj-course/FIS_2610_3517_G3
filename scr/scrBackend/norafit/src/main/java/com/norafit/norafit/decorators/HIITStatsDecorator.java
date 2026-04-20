package com.norafit.norafit.decorators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.norafit.norafit.entities.HIITCardio;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.services.IHIITExecutionService;

@Service
@Primary
public class HIITStatsDecorator extends HIITExecutionServiceDecorator {

    public HIITStatsDecorator(@Qualifier("baseHIITExecutionService") IHIITExecutionService wrappedService) {
        super(wrappedService);
    }

    @Override
    public List<String> executeRoutine(Routine routine, boolean realTime) {
        List<String> events = new ArrayList<>(super.executeRoutine(routine, realTime));

        if (routine == null || routine.getExercises() == null) {
            throw new IllegalArgumentException("La rutina y sus ejercicios son obligatorios.");
        }

        List<HIITCardio> hiitExercises = routine.getExercises().stream()
                .filter(HIITCardio.class::isInstance)
                .map(HIITCardio.class::cast)
                .toList();

        int totalExercises = hiitExercises.size();

        int totalRounds = hiitExercises.stream()
                .mapToInt(HIITCardio::getRounds)
                .sum();

        int totalWorkSeconds = hiitExercises.stream()
                .mapToInt(h -> h.getRounds() * h.getWorkTimeSeconds())
                .sum();

        int totalRestSeconds = hiitExercises.stream()
                .mapToInt(h -> Math.max(0, h.getRounds() - 1) * h.getRestTimeSeconds())
                .sum();

        int totalEstimatedSeconds = totalWorkSeconds + totalRestSeconds;

        events.add("=== RESUMEN FINAL ===");
        events.add("Total ejercicios HIIT: " + totalExercises);
        events.add("Total rondas: " + totalRounds);
        events.add("Tiempo total de trabajo: " + totalWorkSeconds + "s");
        events.add("Tiempo total de descanso: " + totalRestSeconds + "s");
        events.add("Tiempo estimado total: " + totalEstimatedSeconds + "s");

        return events;
    }

    @Override
    public List<String> executeSingleExercise(HIITCardio hiit, boolean realTime) {
        List<String> events = new ArrayList<>(super.executeSingleExercise(hiit, realTime));

        if (hiit == null) {
            throw new IllegalArgumentException("El ejercicio HIIT es obligatorio.");
        }

        int totalWorkSeconds = hiit.getRounds() * hiit.getWorkTimeSeconds();
        int totalRestSeconds = Math.max(0, hiit.getRounds() - 1) * hiit.getRestTimeSeconds();
        int totalEstimatedSeconds = totalWorkSeconds + totalRestSeconds;

        events.add("=== RESUMEN DEL EJERCICIO ===");
        events.add("Rondas: " + hiit.getRounds());
        events.add("Tiempo total de trabajo: " + totalWorkSeconds + "s");
        events.add("Tiempo total de descanso: " + totalRestSeconds + "s");
        events.add("Tiempo estimado total: " + totalEstimatedSeconds + "s");

        return events;
    }
}
