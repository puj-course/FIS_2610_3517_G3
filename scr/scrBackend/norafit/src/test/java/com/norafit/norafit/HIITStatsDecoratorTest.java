package com.norafit.norafit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.norafit.norafit.decorators.HIITStatsDecorator;
import com.norafit.norafit.entities.HIITCardio;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.services.IHIITExecutionService;

class HIITStatsDecoratorTest {

    @Test
    void executeSingleExercise_ShouldAddExerciseSummary() {
        IHIITExecutionService wrappedService = mock(IHIITExecutionService.class);

        HIITCardio hiit = new HIITCardio();
        hiit.setRounds(3);
        hiit.setWorkTimeSeconds(20);
        hiit.setRestTimeSeconds(10);

        when(wrappedService.executeSingleExercise(hiit, false))
                .thenReturn(List.of("Evento base"));

        HIITStatsDecorator decorator = new HIITStatsDecorator(wrappedService);

        List<String> result = decorator.executeSingleExercise(hiit, false);

        assertTrue(result.contains("Evento base"));
        assertTrue(result.contains("=== RESUMEN DEL EJERCICIO ==="));
        assertTrue(result.contains("Rondas: 3"));
        assertTrue(result.contains("Tiempo total de trabajo: 60s"));
        assertTrue(result.contains("Tiempo total de descanso: 20s"));
        assertTrue(result.contains("Tiempo estimado total: 80s"));

        verify(wrappedService).executeSingleExercise(hiit, false);
    }

    @Test
    void executeSingleExercise_WhenOneRound_ShouldHaveZeroRestTime() {
        IHIITExecutionService wrappedService = mock(IHIITExecutionService.class);

        HIITCardio hiit = new HIITCardio();
        hiit.setRounds(1);
        hiit.setWorkTimeSeconds(30);
        hiit.setRestTimeSeconds(15);

        when(wrappedService.executeSingleExercise(hiit, false))
                .thenReturn(List.of("Base"));

        HIITStatsDecorator decorator = new HIITStatsDecorator(wrappedService);

        List<String> result = decorator.executeSingleExercise(hiit, false);

        assertTrue(result.contains("Tiempo total de trabajo: 30s"));
        assertTrue(result.contains("Tiempo total de descanso: 0s"));
        assertTrue(result.contains("Tiempo estimado total: 30s"));
    }

    @Test
    void executeSingleExercise_WhenHiitIsNull_ShouldThrowException() {
        IHIITExecutionService wrappedService = mock(IHIITExecutionService.class);

        when(wrappedService.executeSingleExercise(null, false))
                .thenReturn(List.of("Base"));

        HIITStatsDecorator decorator = new HIITStatsDecorator(wrappedService);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> decorator.executeSingleExercise(null, false)
        );

        assertEquals("El ejercicio HIIT es obligatorio.", exception.getMessage());
    }

    @Test
    void executeRoutine_ShouldAddFinalSummary() {
        IHIITExecutionService wrappedService = mock(IHIITExecutionService.class);

        Routine routine = new Routine();

        HIITCardio hiit1 = new HIITCardio();
        hiit1.setRounds(2);
        hiit1.setWorkTimeSeconds(20);
        hiit1.setRestTimeSeconds(10);

        HIITCardio hiit2 = new HIITCardio();
        hiit2.setRounds(3);
        hiit2.setWorkTimeSeconds(15);
        hiit2.setRestTimeSeconds(5);

        routine.addExercise(hiit1);
        routine.addExercise(hiit2);

        when(wrappedService.executeRoutine(routine, false))
                .thenReturn(List.of("Rutina base"));

        HIITStatsDecorator decorator = new HIITStatsDecorator(wrappedService);

        List<String> result = decorator.executeRoutine(routine, false);

        assertTrue(result.contains("Rutina base"));
        assertTrue(result.contains("=== RESUMEN FINAL ==="));
        assertTrue(result.contains("Total ejercicios HIIT: 2"));
        assertTrue(result.contains("Total rondas: 5"));
        assertTrue(result.contains("Tiempo total de trabajo: 85s"));
        assertTrue(result.contains("Tiempo total de descanso: 30s"));
        assertTrue(result.contains("Tiempo estimado total: 115s"));

        verify(wrappedService).executeRoutine(routine, false);
    }

    @Test
    void executeRoutine_WhenRoutineIsNull_ShouldThrowException() {
        IHIITExecutionService wrappedService = mock(IHIITExecutionService.class);

        when(wrappedService.executeRoutine(null, false))
                .thenReturn(List.of("Base"));

        HIITStatsDecorator decorator = new HIITStatsDecorator(wrappedService);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> decorator.executeRoutine(null, false)
        );

        assertEquals("La rutina y sus ejercicios son obligatorios.", exception.getMessage());
    }

    @Test
    void routineHasHIIT_ShouldDelegateToWrappedService() {
        IHIITExecutionService wrappedService = mock(IHIITExecutionService.class);
        Routine routine = new Routine();

        when(wrappedService.routineHasHIIT(routine)).thenReturn(true);

        HIITStatsDecorator decorator = new HIITStatsDecorator(wrappedService);

        assertTrue(decorator.routineHasHIIT(routine));
        verify(wrappedService).routineHasHIIT(routine);
    }
}
