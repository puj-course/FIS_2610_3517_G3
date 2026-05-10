package com.norafit.norafit.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.norafit.norafit.dto.request.ChangePasswordRequest;
import com.norafit.norafit.dto.request.LoginRequest;
import com.norafit.norafit.dto.request.RegisterRequest;
import com.norafit.norafit.dto.response.UserResponse;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request.username(), request.email(), request.password());
        return ResponseEntity.ok(toResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        User user = authService.login(request.email(), request.password());
        return ResponseEntity.ok(toResponse(user));
    }

    @PutMapping("/change-password")
    public ResponseEntity<UserResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        User user = authService.changePassword(request.email(), request.newPassword());
        return ResponseEntity.ok(toResponse(user));
    }

    private UserResponse toResponse(User user) {
    return new UserResponse(
        (long) user.getId(),   // cast a Long
        user.getUsername(),
        user.getEmail(),
        user.getRole(),
        user.getCreatedAt()
    );
}
}
