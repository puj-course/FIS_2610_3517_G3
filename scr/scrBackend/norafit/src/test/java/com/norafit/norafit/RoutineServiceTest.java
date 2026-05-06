package com.norafit.norafit;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;

import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.repositories.RoutineRepository;
import com.norafit.norafit.services.RoutineService;

@ExtendWith(MockitoExtension.class)
class RoutineServiceTest {

    @Mock
    private RoutineRepository routineRepository;

    @InjectMocks
    private RoutineService routineService;

    @Test
    void createRoutine_WhenNameIsValid_ShouldSaveRoutine() {
        User user = new User();
        user.setId(1);

        when(routineRepository.save(any(Routine.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Routine result = routineService.createRoutine("Pierna", user);

        assertEquals("Pierna", result.getRoutineName());
        assertEquals(user, result.getUser());
        assertEquals(0, result.getTotalTimeSeconds());
        assertNotNull(result.getCreatedAt());

        verify(routineRepository).save(any(Routine.class));
    }

    @Test
    void createRoutine_WhenNameIsBlank_ShouldThrowException() {
        User user = new User();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> routineService.createRoutine("", user)
        );

        assertEquals("El nombre de la rutina no puede estar vacío.", exception.getMessage());
        verify(routineRepository, never()).save(any(Routine.class));
    }

    @Test
    void getRoutinesByUser_ShouldReturnUserRoutines() {
        User user = new User();
        user.setId(1);

        when(routineRepository.findByUser_Id(1L)).thenReturn(List.of(new Routine(), new Routine()));

        List<Routine> result = routineService.getRoutinesByUser(user);

        assertEquals(2, result.size());
        verify(routineRepository).findByUser_Id(1L);
    }

    @Test
    void renameRoutine_WhenUserOwnsRoutine_ShouldUpdateName() {
        User user = new User();
        user.setId(1);

        Routine routine = new Routine();
        routine.setId(10L);
        routine.setRoutineName("Viejo");
        routine.setUser(user);

        when(routineRepository.findById(10L)).thenReturn(Optional.of(routine));
        when(routineRepository.save(any(Routine.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Routine result = routineService.renameRoutine(10L, "Nuevo", user);

        assertEquals("Nuevo", result.getRoutineName());
        verify(routineRepository).save(routine);
    }

    @Test
    void renameRoutine_WhenNewNameIsBlank_ShouldThrowException() {
        User user = new User();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> routineService.renameRoutine(10L, "", user)
        );

        assertEquals("El nuevo nombre no puede estar vacío.", exception.getMessage());
        verify(routineRepository, never()).save(any(Routine.class));
    }

    @Test
    void renameRoutine_WhenRoutineDoesNotBelongToUser_ShouldThrowException() {
        User owner = new User();
        owner.setId(1);

        User anotherUser = new User();
        anotherUser.setId(2);

        Routine routine = new Routine();
        routine.setId(10L);
        routine.setUser(owner);

        when(routineRepository.findById(10L)).thenReturn(Optional.of(routine));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> routineService.renameRoutine(10L, "Nuevo", anotherUser)
        );

        assertEquals("No tienes permiso para editar esta rutina.", exception.getMessage());
        verify(routineRepository, never()).save(any(Routine.class));
    }

    @Test
    void removeRoutine_WhenUserOwnsRoutine_ShouldDeleteRoutine() {
        User user = new User();
        user.setId(1);

        Routine routine = new Routine();
        routine.setId(10L);
        routine.setUser(user);

        when(routineRepository.findById(10L)).thenReturn(Optional.of(routine));

        routineService.removeRoutine(10L, user);

        verify(routineRepository).delete(routine);
    }

    @Test
    void removeRoutine_WhenRoutineDoesNotBelongToUser_ShouldThrowException() {
        User owner = new User();
        owner.setId(1);

        User anotherUser = new User();
        anotherUser.setId(2);

        Routine routine = new Routine();
        routine.setId(10L);
        routine.setUser(owner);

        when(routineRepository.findById(10L)).thenReturn(Optional.of(routine));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> routineService.removeRoutine(10L, anotherUser)
        );

        assertEquals("No tienes permiso para eliminar esta rutina.", exception.getMessage());
        verify(routineRepository, never()).delete(any(Routine.class));
    }
}
