package com.norafit.norafit.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.HIITCardio;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.observer.HIITEvent;
import com.norafit.norafit.observer.HIITEventData;
import com.norafit.norafit.observer.IHIITObservable;
import com.norafit.norafit.observer.IHIITObserver;

@Service("baseHIITExecutionService")
public class HIITExecutionService implements IHIITExecutionService, IHIITObservable {

    private final List<IHIITObserver> observers = new ArrayList<>();

    // interfaz IHIITObservable 

    @Override
    public void addObserver(IHIITObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IHIITObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(HIITEventData data) {
        for (IHIITObserver observer : observers) {
            observer.onEvent(data);
        }
    }

    // interfaz IHIITExecutionService 

    @Override
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

        notifyObservers(new HIITEventData(HIITEvent.ROUTINE_STARTED, "=== INICIO DE RUTINA HIIT: " + routine.getRoutineName() + " ==="));
        events.add("=== INICIO DE RUTINA HIIT: " + routine.getRoutineName() + " ===");

        int totalExercises = hiitExercises.size();
        for (int i = 0; i < totalExercises; i++) {
            HIITCardio hiit = hiitExercises.get(i);
            events.addAll(executeExercise(hiit, i + 1, totalExercises, realTime));
        }

        notifyObservers(new HIITEventData(HIITEvent.ROUTINE_FINISHED, "=== FIN DE RUTINA HIIT ==="));
        events.add("=== FIN DE RUTINA HIIT ===");

        return events;
    }

    @Override
    public List<String> executeSingleExercise(HIITCardio hiit, boolean realTime) {
        
        if (hiit == null) {
            throw new IllegalArgumentException("El ejercicio HIIT es obligatorio.");
        }

        List<String> events = new ArrayList<>();

        notifyObservers(new HIITEventData(HIITEvent.ROUTINE_STARTED, "=== INICIO DE EJERCICIO HIIT ==="));
        events.add("=== INICIO DE EJERCICIO HIIT ===");

        events.addAll(executeExercise(hiit, 1, 1, realTime));

        notifyObservers(new HIITEventData(HIITEvent.ROUTINE_FINISHED, "=== FIN DE EJERCICIO HIIT ==="));
        events.add("=== FIN DE EJERCICIO HIIT ===");

        return events;
    }

    @Override
    public boolean routineHasHIIT(Routine routine) {
        
        if (routine == null || routine.getExercises() == null) return false;
        for (Exercise exercise : routine.getExercises()) {
            if (exercise instanceof HIITCardio) return true;
        }
        return false;
    }

    // Lógica interna 

    private List<String> executeExercise(HIITCardio hiit, int exerciseNumber, int totalExercises, boolean realTime) {
        
        validateHIITConfiguration(hiit);

        List<String> events = new ArrayList<>();
        String header = "Ejercicio " + exerciseNumber + "/" + totalExercises
                + ": " + hiit.getExerciseName();
        String config = "Configuración -> rondas: " + hiit.getRounds()
                + ", trabajo: " + hiit.getWorkTimeSeconds() + "s"
                + ", descanso: " + hiit.getRestTimeSeconds() + "s";

        notifyObservers(new HIITEventData(HIITEvent.EXERCISE_STARTED, header, hiit.getExerciseName(), 0, hiit.getRounds(), 0));
        events.add(header);
        events.add(config);

        for (int round = 1; round <= hiit.getRounds(); round++) {
            String roundMsg = "Ronda " + round + " de " + hiit.getRounds();
            notifyObservers(new HIITEventData(HIITEvent.ROUND_STARTED, roundMsg, hiit.getExerciseName(), round, hiit.getRounds(), 0));
            events.add(roundMsg);

            countdown(HIITEvent.WORK_TICK, HIITEvent.WORK_FINISHED,
                    "TRABAJO", hiit.getWorkTimeSeconds(),
                    hiit.getExerciseName(), round, hiit.getRounds(),
                    realTime, events);

            if (round < hiit.getRounds() && hiit.getRestTimeSeconds() > 0) {
                countdown(HIITEvent.REST_TICK, HIITEvent.REST_FINISHED,
                        "DESCANSO", hiit.getRestTimeSeconds(),
                        hiit.getExerciseName(), round, hiit.getRounds(),
                        realTime, events);
            }
        }

        notifyObservers(new HIITEventData(HIITEvent.EXERCISE_FINISHED,
                "Ejercicio HIIT finalizado: " + hiit.getExerciseName(),
                hiit.getExerciseName(), hiit.getRounds(), hiit.getRounds(), 0));
        events.add("Ejercicio HIIT finalizado: " + hiit.getExerciseName());

        return events;
    }

    private void countdown(HIITEvent tickEvent, HIITEvent finishedEvent, String label, int seconds, String exerciseName, int round, int totalRounds, boolean realTime, List<String> events) {

        for (int remaining = seconds; remaining >= 1; remaining--) {
            notifyObservers(new HIITEventData(tickEvent,
                    label + " - " + remaining + "s restantes",
                    exerciseName, round, totalRounds, remaining));
            events.add(label + " - " + remaining + "s restantes");
            if (realTime) sleepOneSecond();
        }

        notifyObservers(new HIITEventData(finishedEvent, label + " finalizado", exerciseName, round, totalRounds, 0));
    }

    private void validateHIITConfiguration(HIITCardio hiit) {
        if (hiit == null)
            throw new IllegalArgumentException("El ejercicio HIIT es obligatorio.");
        if (hiit.getRounds() <= 0)
            throw new IllegalArgumentException("Las rondas del HIIT deben ser mayores a 0.");
        if (hiit.getWorkTimeSeconds() <= 0)
            throw new IllegalArgumentException("El tiempo de trabajo debe ser mayor a 0.");
        if (hiit.getRestTimeSeconds() < 0)
            throw new IllegalArgumentException("El tiempo de descanso no puede ser negativo.");
    }

    private void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("La ejecución HIIT fue interrumpida.", e);
        }
    }
}
