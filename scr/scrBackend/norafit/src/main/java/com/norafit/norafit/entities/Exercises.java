package com.norafit.norafit.entities;
import java.util.ArrayList;
import java.util.List;


public class Exercises {
    private int exerciseId;
    private String exerciseName;
    private String description;
    private List<Series> series = new ArrayList<>();
    private int durationSeconds;

    // Constructor vacío 
    public Exercises() {
    }

    // Constructor sin lista
    public Exercises(int exerciseId, String exerciseName, String description) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.description = description;
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

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
