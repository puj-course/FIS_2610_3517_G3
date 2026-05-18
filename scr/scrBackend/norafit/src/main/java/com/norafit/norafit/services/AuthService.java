package com.norafit.norafit.services;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.norafit.norafit.entities.User;
import com.norafit.norafit.repositories.UserRepository;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final SmsService smsService;

    public AuthService(UserRepository userRepository, SmsService smsService) {
        this.userRepository = userRepository;
        this.smsService = smsService;
    }

    /**
     * Registra un nuevo usuario (sin verificar) y envía SMS con código OTP.
     * El usuario queda con verified=false hasta completar la verificación.
     */
    public User register(String username, String email, String password, String phoneNumber) {

        // 1) Validaciones básicas
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("El username es obligatorio.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("El número de teléfono es obligatorio.");
        }

        // 2) Validar que el email no exista
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }

        // 3) Crear usuario no verificado
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // TODO: hashear en producción
        user.setRole('U');
        user.setCreatedAt(LocalDate.now());
        user.setPhoneNumber(phoneNumber);
        user.setVerified(false);

        User saved = userRepository.save(user);
        log.info("[AUTH] Usuario registrado (pendiente verificación): {} | teléfono: {}", email, phoneNumber);

        // 4) Enviar SMS de verificación
        smsService.sendVerificationCode(phoneNumber);

        return saved;
    }

    // Verifica el código OTP ingresado por el usuario. Si es correcto, marca la cuenta como verificada.
    public User verifySmsCode(String phoneNumber, String code) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("El teléfono es obligatorio.");
        }
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("El código es obligatorio.");
        }

        boolean valid = smsService.verifyCode(phoneNumber, code);

        if (!valid) {
            log.warn("[AUTH] Código SMS inválido para teléfono: {}", phoneNumber);
            throw new IllegalArgumentException("Código de verificación incorrecto.");
        }

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado para ese número."));

        user.setVerified(true);
        User saved = userRepository.save(user);
        log.info("[AUTH] Cuenta verificada exitosamente para: {} | teléfono: {}", user.getEmail(), phoneNumber);

        return saved;
    }
    
   // Login — solo permite el acceso si la cuenta está verificada.
    public User login(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }

        if (!user.isVerified()) {
            log.warn("[AUTH] Intento de login con cuenta no verificada: {}", email);
            throw new IllegalArgumentException("Cuenta no verificada. Revisa el SMS enviado a tu teléfono.");
        }

        log.info("[AUTH] Login exitoso: {}", email);
        return user;
    }
    
   // Cambio de contraseña.
    public User changePassword(String email, String newPassword) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("La nueva contraseña es obligatoria.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
