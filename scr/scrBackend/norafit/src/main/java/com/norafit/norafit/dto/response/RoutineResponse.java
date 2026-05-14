package com.norafit.norafit.dto.response;

import java.time.LocalDate;
import java.util.List;

public record RoutineResponse(
    Long id,
    String routineName,
    LocalDate createdAt,
    float totalTimeSeconds,
    List<ExerciseInfo> exercises      
) {
    public record ExerciseInfo(Long id, String exerciseName, String type) {}
}