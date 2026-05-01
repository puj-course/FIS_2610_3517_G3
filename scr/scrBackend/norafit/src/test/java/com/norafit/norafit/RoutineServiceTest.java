package com.norafit.norafit;

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
    void createRoutine_WhenDataIsValid_ShouldCreateRoutineForUser() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setUsername("santi");

        when(routineRepository.save(any(Routine.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Routine result = routineService.createRoutine("Rutina fuerza", user);

        // Assert
        assertNotNull(result);
        assertEquals("Rutina fuerza", result.getRoutineName());
        assertEquals(user, result.getUser());
        assertEquals(0, result.getTotalTimeSeconds());
        verify(routineRepository).save(any(Routine.class));
    }

    @Test
    void createRoutine_WhenNameIsBlank_ShouldThrowException() {
        // Arrange
        User user = new User();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routineService.createRoutine("   ", user));

        assertEquals("El nombre de la rutina no puede estar vacío.", exception.getMessage());
        verify(routineRepository, never()).save(any(Routine.class));
    }
}
