package com.norafit.norafit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "cardio_exercises")
@PrimaryKeyJoinColumn(name = "exercise_id")
public abstract class CardioExercise extends Exercise {

    private int durationMinutes;
    private String intensity;
    private String machineType;

    public CardioExercise() {
        super();
    }

    // Getters y Setters básicos
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getIntensity() { return intensity; }
    public void setIntensity(String intensity) { this.intensity = intensity; }
    public String getMachineType() { return machineType; }
    public void setMachineType(String machineType) { this.machineType = machineType; }
}