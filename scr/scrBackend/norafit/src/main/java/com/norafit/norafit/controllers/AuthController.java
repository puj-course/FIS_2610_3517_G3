package com.norafit.norafit.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.norafit.norafit.dto.request.ChangePasswordRequest;
import com.norafit.norafit.dto.request.LoginRequest;
import com.norafit.norafit.dto.request.RegisterRequest;
import com.norafit.norafit.dto.request.VerifySmsRequest;
import com.norafit.norafit.dto.response.UserResponse;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Registra el usuario y envia el SMS con el codigo OTP.
    // Body: { username, email, password, phoneNumber }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = authService.register(
                    request.username(),
                    request.email(),
                    request.password(),
                    request.phoneNumber()
            );
            log.info("[CONTROLLER] Registro exitoso. SMS enviado a: {}", request.phoneNumber());
            return ResponseEntity.ok(toResponse(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Verifica el código OTP recibido por SMS.
    // Body: { phoneNumber, code }
    @PostMapping("/verify-sms")
    public ResponseEntity<?> verifySms(@RequestBody VerifySmsRequest request) {
        try {
            User user = authService.verifySmsCode(request.phoneNumber(), request.code());
            log.info("[CONTROLLER] Verificación SMS exitosa para: {}", request.phoneNumber());
            return ResponseEntity.ok(toResponse(user));
        } catch (IllegalArgumentException e) {
            log.warn("[CONTROLLER] Verificación SMS fallida: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Login — requiere cuenta verificada.
    // Body: { email, password }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = authService.login(request.email(), request.password());
            return ResponseEntity.ok(toResponse(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Body: { email, newPassword }
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            User user = authService.changePassword(request.email(), request.newPassword());
            return ResponseEntity.ok(toResponse(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                (long) user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
