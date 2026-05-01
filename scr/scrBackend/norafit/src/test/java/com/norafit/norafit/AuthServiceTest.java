package com.norafit.norafit;

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

import com.norafit.norafit.entities.User;
import com.norafit.norafit.repositories.UserRepository;
import com.norafit.norafit.services.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_WhenDataIsValid_ShouldCreateUser() {
        // Arrange
        when(userRepository.existsByEmail("santi@test.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = authService.register("santi", "santi@test.com", "1234");

        // Assert
        assertNotNull(result);
        assertEquals("santi", result.getUsername());
        assertEquals("santi@test.com", result.getEmail());
        assertEquals("1234", result.getPassword());
        assertEquals('U', result.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_WhenEmailAlreadyExists_ShouldThrowException() {
        // Arrange
        when(userRepository.existsByEmail("santi@test.com")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authService.register("santi", "santi@test.com", "1234"));

        assertEquals("El email ya está registrado.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_WhenCredentialsAreValid_ShouldReturnUser() {
        // Arrange
        User user = new User();
        user.setEmail("santi@test.com");
        user.setPassword("1234");
        user.setUsername("santi");

        when(userRepository.findByEmail("santi@test.com")).thenReturn(Optional.of(user));

        // Act
        User result = authService.login("santi@test.com", "1234");

        // Assert
        assertEquals("santi@test.com", result.getEmail());
        assertEquals("santi", result.getUsername());
    }
}
