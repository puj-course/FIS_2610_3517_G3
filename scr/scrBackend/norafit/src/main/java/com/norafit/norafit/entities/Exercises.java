package com.norafit.norafit.entities;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "exercises")

public class Exercises {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name ="id")
    private Integer exerciseId;

    @Column(name = "routine_id")
    private Integer routineId;

    @Column(name ="exercise_name")
    private String exerciseName;

    @Column(name="description")
    private String description;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(name = "default_rest_seconds")
    private Integer defaultRestSeconds;

    @Column(name = "ordering")
    private Integer ordering;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Transient
    private List<Series> series = new ArrayList<>();
    
    // Constructor vacío 
    public Exercises() {
    }

    // Constructor sin lista
     public Exercises(Integer exerciseId, Integer routineId, String exerciseName, String description, Integer durationSeconds, 
     Integer defaultRestSeconds, Integer ordering, LocalDateTime createdAt) {

        this.exerciseId = exerciseId;
        this.routineId = routineId;
        this.exerciseName = exerciseName;
        this.description = description;
        this.durationSeconds = durationSeconds;
        this.defaultRestSeconds = defaultRestSeconds;
        this.ordering = ordering;
        this.createdAt = createdAt;
    }

    // Constructor completo
    public Exercises(int exerciseId, String exerciseName, String description, List<Series> series) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.description = description;
        this.series = series;
    }

    //getters y setters
    public int getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getRoutineId() {
        return routineId;
    }

    public void setRoutineId(Integer routineId) {
        this.routineId = routineId;
    }

    public String getExerciseName() {
        return exerciseName;
    }
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Integer getDefaultRestSeconds() {
        return defaultRestSeconds;
    }

    public void setDefaultRestSeconds(Integer defaultRestSeconds) {
        this.defaultRestSeconds = defaultRestSeconds;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Series> getSeries() {
        return series;
    }
    public void setSeries(List<Series> series) {
        this.series = series;
    }

}
