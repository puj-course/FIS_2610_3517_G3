package com.norafit.norafit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.facade.RoutineManagementFacade;
import com.norafit.norafit.services.RoutineService;

class RoutineManagementFacadeTest {

    @Test
    void createRoutine_WhenUserIsValid_ShouldCallService() {
        RoutineService routineService = mock(RoutineService.class);
        RoutineManagementFacade facade = new RoutineManagementFacade(routineService);

        User user = new User();
        Routine routine = new Routine();

        when(routineService.createRoutine("Pierna", user)).thenReturn(routine);

        Routine result = facade.createRoutine("Pierna", user);

        assertEquals(routine, result);
        verify(routineService).createRoutine("Pierna", user);
    }

    @Test
    void createRoutine_WhenUserIsNull_ShouldThrowException() {
        RoutineService routineService = mock(RoutineService.class);
        RoutineManagementFacade facade = new RoutineManagementFacade(routineService);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> facade.createRoutine("Pierna", null)
        );

        assertEquals("El usuario es obligatorio.", exception.getMessage());
        verify(routineService, never()).createRoutine(anyString(), any());
    }

    @Test
    void listUserRoutines_WhenUserIsValid_ShouldCallService() {
        RoutineService routineService = mock(RoutineService.class);
        RoutineManagementFacade facade = new RoutineManagementFacade(routineService);

        User user = new User();
        List<Routine> routines = List.of(new Routine(), new Routine());

        when(routineService.getRoutinesByUser(user)).thenReturn(routines);

        List<Routine> result = facade.listUserRoutines(user);

        assertEquals(2, result.size());
        verify(routineService).getRoutinesByUser(user);
    }

    @Test
    void listUserRoutines_WhenUserIsNull_ShouldThrowException() {
        RoutineService routineService = mock(RoutineService.class);
        RoutineManagementFacade facade = new RoutineManagementFacade(routineService);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> facade.listUserRoutines(null)
        );

        assertEquals("El usuario es obligatorio.", exception.getMessage());
        verify(routineService, never()).getRoutinesByUser(any());
    }

    @Test
    void renameRoutine_WhenDataIsValid_ShouldCallService() {
        RoutineService routineService = mock(RoutineService.class);
        RoutineManagementFacade facade = new RoutineManagementFacade(routineService);

        User user = new User();
        Routine routine = new Routine();

        when(routineService.renameRoutine(1L, "Nueva", user)).thenReturn(routine);

        Routine result = facade.renameRoutine(1L, "Nueva", user);

        assertEquals(routine, result);
        verify(routineService).renameRoutine(1L, "Nueva", user);
    }

    @Test
    void renameRoutine_WhenIdIsNull_ShouldThrowException() {
        RoutineService routineService = mock(RoutineService.class);
        RoutineManagementFacade facade = new RoutineManagementFacade(routineService);

        User user = new User();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> facade.renameRoutine(null, "Nueva", user)
        );

        assertEquals("El ID de la rutina es obligatorio.", exception.getMessage());
        verify(routineService, never()).renameRoutine(any(), anyString(), any());
    }

    @Test
    void renameRoutine_WhenUserIsNull_ShouldThrowException() {
        RoutineService routineService = mock(RoutineService.class);
        RoutineManagementFacade facade = new RoutineManagementFacade(routineService);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> facade.renameRoutine(1L, "Nueva", null)
        );

        assertEquals("El usuario es obligatorio.", exception.getMessage());
        verify(routineService, never()).renameRoutine(any(), anyString(), any());
    }

    @Test
    void removeRoutine_WhenDataIsValid_ShouldCallService() {
        RoutineService routineService = mock(RoutineService.class);
        RoutineManagementFacade facade = new RoutineManagementFacade(routineService);

        User user = new User();

        facade.removeRoutine(1L, user);

        verify(routineService).removeRoutine(1L, user);
    }

    @Test
    void removeRoutine_WhenIdIsNull_ShouldThrowException() {
        RoutineService routineService = mock(RoutineService.class);
        RoutineManagementFacade facade = new RoutineManagementFacade(routineService);

        User user = new User();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> facade.removeRoutine(null, user)
        );

        assertEquals("El ID de la rutina es obligatorio.", exception.getMessage());
        verify(routineService, never()).removeRoutine(any(), any());
    }

    @Test
    void removeRoutine_WhenUserIsNull_ShouldThrowException() {
        RoutineService routineService = mock(RoutineService.class);
        RoutineManagementFacade facade = new RoutineManagementFacade(routineService);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> facade.removeRoutine(1L, null)
        );

        assertEquals("El usuario es obligatorio.", exception.getMessage());
        verify(routineService, never()).removeRoutine(any(), any());
    }

    @Test
    void getRoutineById_WhenIdIsValid_ShouldCallService() {
        RoutineService routineService = mock(RoutineService.class);
        RoutineManagementFacade facade = new RoutineManagementFacade(routineService);

        Routine routine = new Routine();

        when(routineService.getRoutineById(1L)).thenReturn(routine);

        Routine result = facade.getRoutineById(1L);

        assertEquals(routine, result);
        verify(routineService).getRoutineById(1L);
    }

    @Test
    void getRoutineById_WhenIdIsNull_ShouldThrowException() {
        RoutineService routineService = mock(RoutineService.class);
        RoutineManagementFacade facade = new RoutineManagementFacade(routineService);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> facade.getRoutineById(null)
        );

        assertEquals("El ID de la rutina es obligatorio.", exception.getMessage());
        verify(routineService, never()).getRoutineById(any());
    }
}
