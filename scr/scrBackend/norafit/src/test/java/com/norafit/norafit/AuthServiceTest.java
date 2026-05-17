package com.norafit.norafit;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.norafit.norafit.entities.User;
import com.norafit.norafit.repositories.UserRepository;
import com.norafit.norafit.services.AuthService;
import com.norafit.norafit.services.SmsService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SmsService smsService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_WithPhone_WhenDataIsValid_ShouldCreateUserAndSendSms() {
        when(userRepository.existsByEmail("santi@test.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(smsService).sendVerificationCode("+573000000000");

        User result = authService.register("santi", "santi@test.com", "1234", "+573000000000");

        assertNotNull(result);
        assertEquals("santi", result.getUsername());
        assertEquals("santi@test.com", result.getEmail());
        assertEquals("1234", result.getPassword());
        assertEquals('U', result.getRole());
        assertNotNull(result.getCreatedAt());
        assertFalse(result.isVerified());

        verify(userRepository).save(any(User.class));
        verify(smsService).sendVerificationCode("+573000000000");
    }

    @Test
    void register_WithPhone_WhenPhoneIsBlank_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register("santi", "santi@test.com", "1234", "")
        );

        assertEquals("El número de teléfono es obligatorio.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_WhenDataIsValid_ShouldCreateUser() {
        when(userRepository.existsByEmail("santi@test.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = authService.register("santi", "santi@test.com", "1234");

        assertNotNull(result);
        assertEquals("santi", result.getUsername());
        assertEquals("santi@test.com", result.getEmail());
        assertEquals("1234", result.getPassword());
        assertEquals('U', result.getRole());
        assertNotNull(result.getCreatedAt());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_WhenUsernameIsBlank_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register("", "santi@test.com", "1234")
        );

        assertEquals("El username es obligatorio.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_WhenEmailIsBlank_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register("santi", "", "1234")
        );

        assertEquals("El email es obligatorio.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_WhenEmailIsInvalid_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register("santi", "correo-invalido", "1234")
        );

        assertEquals("Email inválido.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_WhenPasswordIsBlank_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register("santi", "santi@test.com", "")
        );

        assertEquals("La contraseña es obligatoria.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_WhenEmailAlreadyExists_ShouldThrowException() {
        when(userRepository.existsByEmail("santi@test.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register("santi", "santi@test.com", "1234", "+573000000000")
        );

        assertEquals("El email ya está registrado.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void verifySmsCode_WhenCodeIsCorrect_ShouldActivateAccount() {
        User user = new User();
        user.setEmail("santi@test.com");
        user.setPhoneNumber("+573000000000");
        user.setVerified(false);

        when(smsService.verifyCode("+573000000000", "123456")).thenReturn(true);
        when(userRepository.findByPhoneNumber("+573000000000")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = authService.verifySmsCode("+573000000000", "123456");

        assertTrue(result.isVerified());
        verify(userRepository).save(user);
    }

    @Test
    void verifySmsCode_WhenCodeIsIncorrect_ShouldThrowException() {
        when(smsService.verifyCode("+573000000000", "000000")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.verifySmsCode("+573000000000", "000000")
        );

        assertEquals("Código de verificación incorrecto.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void verifySmsCode_WhenPhoneIsBlank_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.verifySmsCode("", "123456")
        );

        assertEquals("El teléfono es obligatorio.", exception.getMessage());
    }

    @Test
    void verifySmsCode_WhenCodeIsBlank_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.verifySmsCode("+573000000000", "")
        );

        assertEquals("El código es obligatorio.", exception.getMessage());
    }

    @Test
    void login_WhenCredentialsAreValid_ShouldReturnUser() {
        User user = new User();
        user.setEmail("santi@test.com");
        user.setPassword("1234");
        user.setUsername("santi");
        user.setVerified(true);

        when(userRepository.findByEmail("santi@test.com")).thenReturn(Optional.of(user));

        User result = authService.login("santi@test.com", "1234");

        assertEquals("santi@test.com", result.getEmail());
        assertEquals("santi", result.getUsername());
    }

    @Test
    void login_WhenAccountNotVerified_ShouldThrowException() {
        User user = new User();
        user.setEmail("santi@test.com");
        user.setPassword("1234");
        user.setVerified(false);

        when(userRepository.findByEmail("santi@test.com")).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login("santi@test.com", "1234")
        );

        assertEquals("Cuenta no verificada. Revisa el SMS enviado a tu teléfono.", exception.getMessage());
    }

    @Test
    void login_WhenEmailIsBlank_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login("", "1234")
        );

        assertEquals("El email es obligatorio.", exception.getMessage());
    }

    @Test
    void login_WhenPasswordIsBlank_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login("santi@test.com", "")
        );

        assertEquals("La contraseña es obligatoria.", exception.getMessage());
    }

    @Test
    void login_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findByEmail("santi@test.com")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login("santi@test.com", "1234")
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void login_WhenPasswordIsIncorrect_ShouldThrowException() {
        User user = new User();
        user.setEmail("santi@test.com");
        user.setPassword("1234");
        user.setVerified(true);

        when(userRepository.findByEmail("santi@test.com")).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login("santi@test.com", "incorrecta")
        );

        assertEquals("Contraseña incorrecta.", exception.getMessage());
    }

    @Test
    void changePassword_WhenUserExists_ShouldUpdatePassword() {
        User user = new User();
        user.setEmail("santi@test.com");
        user.setPassword("old");

        when(userRepository.findByEmail("santi@test.com")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = authService.changePassword("santi@test.com", "new123");

        assertEquals("new123", result.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void changePassword_WhenNewPasswordIsBlank_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.changePassword("santi@test.com", "")
        );

        assertEquals("La nueva contraseña es obligatoria.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}
