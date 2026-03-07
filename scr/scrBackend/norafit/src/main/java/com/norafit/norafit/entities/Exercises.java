package com.norafit.norafit.entities;
import java.util.ArrayList;
import java.util.List;


public class Exercises {
    private int exerciseId;
    private String exerciseName;
    private String description;
    private List<Series> series = new ArrayList<>();
    

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

    //metodos

    //crear ejercicio
    public Exercises create(int exerciseId, String exerciseName, String description) {
        return new Exercises(exerciseId, exerciseName, description);
    }

    //agregar serie al ejercicio
    public void addSeries(int reps, double weight, int restSec) {
        if (reps <= 0) {
            throw new IllegalArgumentException("Las repeticiones deben ser mayores que 0");
        }

        if (weight < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }

        Series newSeries = new Series(reps, weight, restSec);
        this.series.add(newSeries);
    }
}
