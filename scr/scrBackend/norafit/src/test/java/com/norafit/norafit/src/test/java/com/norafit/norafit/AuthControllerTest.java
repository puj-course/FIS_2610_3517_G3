package com.norafit.norafit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.norafit.norafit.controllers.AuthController;
import com.norafit.norafit.dto.request.ChangePasswordRequest;
import com.norafit.norafit.dto.request.LoginRequest;
import com.norafit.norafit.dto.request.RegisterRequest;
import com.norafit.norafit.dto.response.UserResponse;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.services.AuthService;

class AuthControllerTest {

    @Test
    void register_ShouldReturnUserResponse() {
        AuthService authService = mock(AuthService.class);
        AuthController controller = new AuthController(authService);

        User user = new User();
        user.setId(1);
        user.setUsername("Santiago");
        user.setEmail("santiago@test.com");
        user.setRole('U');
        user.setCreatedAt(LocalDate.of(2026, 5, 12));

        when(authService.register("Santiago", "santiago@test.com", "1234"))
                .thenReturn(user);

        ResponseEntity<UserResponse> response = controller.register(
                new RegisterRequest("Santiago", "santiago@test.com", "1234")
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
        assertEquals("Santiago", response.getBody().username());
        assertEquals("santiago@test.com", response.getBody().email());
        assertEquals('U', response.getBody().role());

        verify(authService).register("Santiago", "santiago@test.com", "1234");
    }

    @Test
    void login_ShouldReturnUserResponse() {
        AuthService authService = mock(AuthService.class);
        AuthController controller = new AuthController(authService);

        User user = new User();
        user.setId(2);
        user.setUsername("Usuario");
        user.setEmail("user@test.com");
        user.setRole('U');
        user.setCreatedAt(LocalDate.of(2026, 5, 12));

        when(authService.login("user@test.com", "abcd"))
                .thenReturn(user);

        ResponseEntity<UserResponse> response = controller.login(
                new LoginRequest("user@test.com", "abcd")
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2L, response.getBody().id());
        assertEquals("Usuario", response.getBody().username());

        verify(authService).login("user@test.com", "abcd");
    }

    @Test
    void changePassword_ShouldReturnUserResponse() {
        AuthService authService = mock(AuthService.class);
        AuthController controller = new AuthController(authService);

        User user = new User();
        user.setId(3);
        user.setUsername("Cambio");
        user.setEmail("cambio@test.com");
        user.setRole('U');
        user.setCreatedAt(LocalDate.of(2026, 5, 12));

        when(authService.changePassword("cambio@test.com", "nueva123"))
                .thenReturn(user);

        ResponseEntity<UserResponse> response = controller.changePassword(
                new ChangePasswordRequest("cambio@test.com", "nueva123")
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(3L, response.getBody().id());
        assertEquals("Cambio", response.getBody().username());

        verify(authService).changePassword("cambio@test.com", "nueva123");
    }
}
