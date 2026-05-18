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

        // NUEVO: RegisterRequest ahora tiene 4 campos (phoneNumber añadido)
        when(authService.register("Santiago", "santiago@test.com", "1234", "+573000000000"))
                .thenReturn(user);

        ResponseEntity<?> response = controller.register(
                new RegisterRequest("Santiago", "santiago@test.com", "1234", "+573000000000")
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        UserResponse body = (UserResponse) response.getBody();
        assertEquals(1L, body.id());
        assertEquals("Santiago", body.username());
        assertEquals("santiago@test.com", body.email());
        assertEquals('U', body.role());

        verify(authService).register("Santiago", "santiago@test.com", "1234", "+573000000000");
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

        ResponseEntity<?> response = controller.login(
                new LoginRequest("user@test.com", "abcd")
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        UserResponse body = (UserResponse) response.getBody();
        assertEquals(2L, body.id());
        assertEquals("Usuario", body.username());

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

        ResponseEntity<?> response = controller.changePassword(
                new ChangePasswordRequest("cambio@test.com", "nueva123")
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        UserResponse body = (UserResponse) response.getBody();
        assertEquals(3L, body.id());
        assertEquals("Cambio", body.username());

        verify(authService).changePassword("cambio@test.com", "nueva123");
    }

    @Test
    void verifySms_ShouldReturnUserResponse() {
        AuthService authService = mock(AuthService.class);
        AuthController controller = new AuthController(authService);

        User user = new User();
        user.setId(4);
        user.setUsername("Verificado");
        user.setEmail("verificado@test.com");
        user.setRole('U');
        user.setCreatedAt(LocalDate.of(2026, 5, 12));
        user.setVerified(true);

        when(authService.verifySmsCode("+573000000000", "123456"))
                .thenReturn(user);

        ResponseEntity<?> response = controller.verifySms(
                new VerifySmsRequest("+573000000000", "123456")
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        UserResponse body = (UserResponse) response.getBody();
        assertEquals(4L, body.id());
        assertEquals("Verificado", body.username());

        verify(authService).verifySmsCode("+573000000000", "123456");
    }
}
