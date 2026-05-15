package com.norafit.norafit.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.norafit.norafit.dto.response.RoutineResponse;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.SimpleCardio;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.facade.RoutineManagementFacade;
import com.norafit.norafit.repositories.UserRepository;

class RoutineControllerTest {

    @Test
    void listRoutines_WhenUserExists_ShouldReturnRoutines() {
        RoutineManagementFacade routineFacade = mock(RoutineManagementFacade.class);
        UserRepository userRepository = mock(UserRepository.class);

        User user = new User();
        user.setId(1);

        Routine routine = new Routine();
        routine.setId(10L);
        routine.setRoutineName("Pierna");
        routine.setCreatedAt(LocalDate.of(2026, 5, 13));
        routine.setTotalTimeSeconds(1200);

        SimpleCardio exercise = new SimpleCardio();
        exercise.setExerciseName("Caminadora");
        routine.addExercise(exercise);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(routineFacade.listUserRoutines(user)).thenReturn(List.of(routine));

        RoutineController controller = new RoutineController(routineFacade, userRepository);

        ResponseEntity<List<RoutineResponse>> response = controller.listRoutines(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Pierna", response.getBody().get(0).routineName());
        
        verify(userRepository).findById(1);
        verify(routineFacade).listUserRoutines(user);
    }

    @Test
    void listRoutines_WhenUserDoesNotExist_ShouldThrowException() {
        RoutineManagementFacade routineFacade = mock(RoutineManagementFacade.class);
        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        RoutineController controller = new RoutineController(routineFacade, userRepository);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.listRoutines(1L)
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(routineFacade, never()).listUserRoutines(any());
    }

    @Test
    void createRoutine_WhenUserExists_ShouldReturnCreatedRoutine() {
        RoutineManagementFacade routineFacade = mock(RoutineManagementFacade.class);
        UserRepository userRepository = mock(UserRepository.class);

        User user = new User();
        user.setId(1);

        Routine routine = new Routine();
        routine.setId(20L);
        routine.setRoutineName("Espalda");
        routine.setCreatedAt(LocalDate.of(2026, 5, 13));
        routine.setTotalTimeSeconds(900);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(routineFacade.createRoutine("Espalda", user)).thenReturn(routine);

        RoutineController controller = new RoutineController(routineFacade, userRepository);

        ResponseEntity<RoutineResponse> response = controller.createRoutine(
                1L,
                new RoutineController.CreateRoutineRequest("Espalda")
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Espalda", response.getBody().routineName());

        verify(userRepository).findById(1);
        verify(routineFacade).createRoutine("Espalda", user);
    }

    @Test
    void createRoutine_WhenUserDoesNotExist_ShouldThrowException() {
        RoutineManagementFacade routineFacade = mock(RoutineManagementFacade.class);
        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        RoutineController controller = new RoutineController(routineFacade, userRepository);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.createRoutine(
                        1L,
                        new RoutineController.CreateRoutineRequest("Espalda")
                )
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(routineFacade, never()).createRoutine(anyString(), any());
    }

    @Test
    void deleteRoutine_WhenUserExists_ShouldRemoveRoutine() {
        RoutineManagementFacade routineFacade = mock(RoutineManagementFacade.class);
        UserRepository userRepository = mock(UserRepository.class);

        User user = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        RoutineController controller = new RoutineController(routineFacade, userRepository);

        ResponseEntity<Void> response = controller.deleteRoutine(10L, 1L);

        assertEquals(200, response.getStatusCode().value());

        verify(userRepository).findById(1);
        verify(routineFacade).removeRoutine(10L, user);
    }

    @Test
    void deleteRoutine_WhenUserDoesNotExist_ShouldThrowException() {
        RoutineManagementFacade routineFacade = mock(RoutineManagementFacade.class);
        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        RoutineController controller = new RoutineController(routineFacade, userRepository);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.deleteRoutine(10L, 1L)
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(routineFacade, never()).removeRoutine(anyLong(), any());
    }

    @Test
    void renameRoutine_WhenUserExists_ShouldReturnRenamedRoutine() {
        RoutineManagementFacade routineFacade = mock(RoutineManagementFacade.class);
        UserRepository userRepository = mock(UserRepository.class);

        User user = new User();
        user.setId(1);

        Routine routine = new Routine();
        routine.setId(10L);
        routine.setRoutineName("Nueva rutina");
        routine.setCreatedAt(LocalDate.of(2026, 5, 13));
        routine.setTotalTimeSeconds(700);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(routineFacade.renameRoutine(10L, "Nueva rutina", user)).thenReturn(routine);

        RoutineController controller = new RoutineController(routineFacade, userRepository);

        ResponseEntity<RoutineResponse> response = controller.renameRoutine(
                10L,
                1L,
                new RoutineController.RenameRoutineRequest("Nueva rutina")
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Nueva rutina", response.getBody().routineName());

        verify(userRepository).findById(1);
        verify(routineFacade).renameRoutine(10L, "Nueva rutina", user);
    }

    @Test
    void renameRoutine_WhenUserDoesNotExist_ShouldThrowException() {
        RoutineManagementFacade routineFacade = mock(RoutineManagementFacade.class);
        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        RoutineController controller = new RoutineController(routineFacade, userRepository);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.renameRoutine(
                        10L,
                        1L,
                        new RoutineController.RenameRoutineRequest("Nueva rutina")
                )
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(routineFacade, never()).renameRoutine(anyLong(), anyString(), any());
    }

    @Test
    void getRoutineById_ShouldReturnRoutine() {
        RoutineManagementFacade routineFacade = mock(RoutineManagementFacade.class);
        UserRepository userRepository = mock(UserRepository.class);

        Routine routine = new Routine();
        routine.setId(10L);
        routine.setRoutineName("Full body");
        routine.setCreatedAt(LocalDate.of(2026, 5, 13));
        routine.setTotalTimeSeconds(1000);

        when(routineFacade.getRoutineById(10L)).thenReturn(routine);

        RoutineController controller = new RoutineController(routineFacade, userRepository);

        ResponseEntity<RoutineResponse> response = controller.getRoutineById(10L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Full body", response.getBody().routineName());

        verify(routineFacade).getRoutineById(10L);
    }
}
