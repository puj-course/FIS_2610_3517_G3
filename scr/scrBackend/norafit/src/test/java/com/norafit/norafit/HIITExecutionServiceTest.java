package com.norafit.norafit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.norafit.norafit.entities.HIITCardio;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.SimpleCardio;
import com.norafit.norafit.observer.HIITEventData;
import com.norafit.norafit.observer.IHIITObserver;
import com.norafit.norafit.services.HIITExecutionService;

class HIITExecutionServiceTest {

    @Test
    void executeSingleExercise_WhenValid_ShouldReturnEventsAndNotifyObserver() {
        HIITExecutionService service = new HIITExecutionService();
        IHIITObserver observer = mock(IHIITObserver.class);
        service.addObserver(observer);

        HIITCardio hiit = new HIITCardio();
        hiit.setExerciseName("Burpees");
        hiit.setRounds(2);
        hiit.setWorkTimeSeconds(2);
        hiit.setRestTimeSeconds(1);

        List<String> events = service.executeSingleExercise(hiit, false);

        assertTrue(events.contains("=== INICIO DE EJERCICIO HIIT ==="));
        assertTrue(events.contains("Ejercicio 1/1: Burpees"));
        assertTrue(events.contains("TRABAJO - 2s restantes"));
        assertTrue(events.contains("DESCANSO - 1s restantes"));
        assertTrue(events.contains("=== FIN DE EJERCICIO HIIT ==="));

        verify(observer, atLeastOnce()).onEvent(any(HIITEventData.class));
    }

    @Test
    void executeRoutine_WhenRoutineHasHIIT_ShouldReturnRoutineEvents() {
        HIITExecutionService service = new HIITExecutionService();

        Routine routine = new Routine();
        routine.setRoutineName("Rutina intensa");

        HIITCardio hiit = new HIITCardio();
        hiit.setExerciseName("Mountain climbers");
        hiit.setRounds(1);
        hiit.setWorkTimeSeconds(1);
        hiit.setRestTimeSeconds(0);

        routine.addExercise(hiit);

        List<String> events = service.executeRoutine(routine, false);

        assertTrue(events.get(0).contains("INICIO DE RUTINA HIIT"));
        assertTrue(events.contains("Ejercicio HIIT finalizado: Mountain climbers"));
        assertTrue(events.get(events.size() - 1).contains("FIN DE RUTINA HIIT"));
    }

    @Test
    void executeRoutine_WhenRoutineIsNull_ShouldThrowException() {
        HIITExecutionService service = new HIITExecutionService();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.executeRoutine(null, false)
        );

        assertEquals("La rutina es obligatoria.", exception.getMessage());
    }

    @Test
    void executeRoutine_WhenNoHIITExercises_ShouldThrowException() {
        HIITExecutionService service = new HIITExecutionService();

        Routine routine = new Routine();
        routine.setRoutineName("Cardio normal");
        routine.addExercise(new SimpleCardio());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.executeRoutine(routine, false)
        );

        assertEquals("La rutina seleccionada no contiene ejercicios HIIT.", exception.getMessage());
    }

    @Test
    void executeSingleExercise_WhenRoundsAreInvalid_ShouldThrowException() {
        HIITExecutionService service = new HIITExecutionService();

        HIITCardio hiit = new HIITCardio();
        hiit.setExerciseName("Burpees");
        hiit.setRounds(0);
        hiit.setWorkTimeSeconds(20);
        hiit.setRestTimeSeconds(10);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.executeSingleExercise(hiit, false)
        );

        assertEquals("Las rondas del HIIT deben ser mayores a 0.", exception.getMessage());
    }

    @Test
    void routineHasHIIT_ShouldReturnExpectedValues() {
        HIITExecutionService service = new HIITExecutionService();

        Routine routine = new Routine();
        routine.addExercise(new SimpleCardio());

        assertFalse(service.routineHasHIIT(null));
        assertFalse(service.routineHasHIIT(routine));

        HIITCardio hiit = new HIITCardio();
        hiit.setRounds(1);
        hiit.setWorkTimeSeconds(1);
        hiit.setRestTimeSeconds(0);

        routine.addExercise(hiit);

        assertTrue(service.routineHasHIIT(routine));
    }
}
