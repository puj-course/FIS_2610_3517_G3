package com.norafit.norafit.dto.response;
import java.time.LocalDate;

public record UserResponse(Long id, String username, String email, char role, LocalDate createdAt) {}