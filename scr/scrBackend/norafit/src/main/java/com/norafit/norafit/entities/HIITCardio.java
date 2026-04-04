package com.norafit.norafit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "hiit_cardio")
@PrimaryKeyJoinColumn(name = "cardio_exercise_id")
public class HIITCardio extends CardioExercise {

    private int rounds;
    private int workTimeSeconds;
    private int restTimeSeconds;

    public HIITCardio() {
        super();
    }

    // Getters y Setters
    public int getRounds() { return rounds; }
    public void setRounds(int rounds) { this.rounds = rounds; }
    public int getWorkTimeSeconds() { return workTimeSeconds; }
    public void setWorkTimeSeconds(int workTimeSeconds) { this.workTimeSeconds = workTimeSeconds; }
    public int getRestTimeSeconds() { return restTimeSeconds; }
    public void setRestTimeSeconds(int restTimeSeconds) { this.restTimeSeconds = restTimeSeconds; }
}