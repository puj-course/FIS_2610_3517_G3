package com.norafit.norafit.entities;

import jakarta.persistence.Entity;

@Entity
public class CardioExercise extends Exercises {

    private Integer intensity;

    public CardioExercise() {
        super();
    }

    public CardioExercise(Integer exerciseId, String exerciseName, String description, Integer durationSeconds, Integer intensity) {
        super();
        this.setExerciseId(exerciseId);
        this.setExerciseName(exerciseName);
        this.setDescription(description);
        this.setDurationSeconds(durationSeconds);
        this.intensity = intensity;
    }

    public Integer calculateCalories() {
        if (getDurationSeconds() == null || intensity == null) return 0;
        return getDurationSeconds() * intensity * 2;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }
}
