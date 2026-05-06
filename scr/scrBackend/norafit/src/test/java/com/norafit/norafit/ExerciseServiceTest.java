package com.norafit.norafit;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.StrengthExercise;
import com.norafit.norafit.factory.StrengthExerciseFactory;
import com.norafit.norafit.repositories.ExerciseRepository;
import com.norafit.norafit.repositories.RoutineRepository;
import com.norafit.norafit.services.ExerciseService;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private RoutineRepository routineRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    @Test
    void addStrengthExercise_WhenRoutineExists_ShouldCreateAndSaveExercise() {
        Routine routine = new Routine();
        routine.setId(1L);

        when(routineRepository.findById(1L)).thenReturn(Optional.of(routine));
        when(exerciseRepository.save(any(Exercise.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Exercise result = exerciseService.addStrengthExercise(1L, "Press banca", "Pecho", true);

        assertTrue(result instanceof StrengthExercise);
        assertEquals("Press banca", result.getExerciseName());
        assertEquals("Pecho", result.getDescription());
        assertEquals(routine, result.getRoutine());
        assertEquals(1, routine.getExercises().size());

        verify(exerciseRepository).save(result);
    }

    @Test
    void addStrengthExercise_WhenRoutineDoesNotExist_ShouldThrowException() {
        when(routineRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> exerciseService.addStrengthExercise(99L, "Press banca", "Pecho", true)
        );

        assertEquals("Error: La rutina con ID 99 no existe.", exception.getMessage());
        verify(exerciseRepository, never()).save(any(Exercise.class));
    }

    @Test
    void renameExercise_WhenNameIsValid_ShouldUpdateName() {
        StrengthExercise exercise = new StrengthExercise();
        exercise.setId(5L);
        exercise.setExerciseName("Viejo");

        when(exerciseRepository.findById(5L)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(any(Exercise.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Exercise result = exerciseService.renameExercise(5L, "Nuevo");

        assertEquals("Nuevo", result.getExerciseName());
        verify(exerciseRepository).save(exercise);
    }

    @Test
    void renameExercise_WhenNameIsBlank_ShouldThrowException() {
        StrengthExercise exercise = new StrengthExercise();
        exercise.setId(5L);

        when(exerciseRepository.findById(5L)).thenReturn(Optional.of(exercise));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> exerciseService.renameExercise(5L, "")
        );

        assertEquals("El nombre del ejercicio no puede estar vacío.", exception.getMessage());
        verify(exerciseRepository, never()).save(any(Exercise.class));
    }

    @Test
    void deleteExercise_WhenExerciseBelongsToRoutine_ShouldDeleteExercise() {
        Routine routine = new Routine();
        routine.setId(1L);

        StrengthExercise exercise = new StrengthExercise();
        exercise.setId(5L);

        routine.addExercise(exercise);

        when(routineRepository.findById(1L)).thenReturn(Optional.of(routine));
        when(exerciseRepository.findById(5L)).thenReturn(Optional.of(exercise));

        exerciseService.deleteExercise(5L, 1L);

        assertNull(exercise.getRoutine());
        verify(exerciseRepository).delete(exercise);
    }

    @Test
    void deleteExercise_WhenExerciseDoesNotBelongToRoutine_ShouldThrowException() {
        Routine realRoutine = new Routine();
        realRoutine.setId(1L);

        Routine otherRoutine = new Routine();
        otherRoutine.setId(2L);

        StrengthExercise exercise = new StrengthExercise();
        exercise.setId(5L);
        exercise.setRoutine(otherRoutine);

        when(routineRepository.findById(1L)).thenReturn(Optional.of(realRoutine));
        when(exerciseRepository.findById(5L)).thenReturn(Optional.of(exercise));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> exerciseService.deleteExercise(5L, 1L)
        );

        assertEquals("El ejercicio no pertenece a la rutina indicada.", exception.getMessage());
        verify(exerciseRepository, never()).delete(any(Exercise.class));
    }

    @Test
    void addExercise_WhenFactoryIsValid_ShouldCreateExercise() {
        Routine routine = new Routine();
        routine.setId(1L);

        StrengthExerciseFactory factory = new StrengthExerciseFactory(true);

        when(routineRepository.findById(1L)).thenReturn(Optional.of(routine));
        when(exerciseRepository.save(any(Exercise.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Exercise result = exerciseService.addExercise(1L, factory, "Sentadilla", "Pierna");

        assertTrue(result instanceof StrengthExercise);
        assertEquals("Sentadilla", result.getExerciseName());
        assertEquals("Pierna", result.getDescription());
        assertEquals(routine, result.getRoutine());

        verify(exerciseRepository).save(result);
    }

    @Test
    void addExercise_WhenFactoryIsNull_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> exerciseService.addExercise(1L, null, "Sentadilla", "Pierna")
        );

        assertEquals("La fábrica de ejercicio es obligatoria.", exception.getMessage());
        verify(exerciseRepository, never()).save(any(Exercise.class));
    }
}
