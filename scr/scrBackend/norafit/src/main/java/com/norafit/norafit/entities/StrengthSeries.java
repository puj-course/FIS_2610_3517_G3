package com.norafit.norafit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "strength_series")
public class StrengthSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int seriesNumber;
    private int repetitions;
    private float weight;
    private int restTimeSeconds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strength_exercise_id")
    private StrengthExercise strengthExercise;

    // Constructor vacío (JPA)
    public StrengthSeries() {}

    // Constructor para facilidad de uso en consola
    public StrengthSeries(int seriesNumber, int repetitions, float weight, int restTimeSeconds) {
        this.seriesNumber = seriesNumber;
        this.repetitions = repetitions;
        this.weight = weight;
        this.restTimeSeconds = restTimeSeconds;
    }

    // --- GETTERS Y SETTERS ---
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public int getSeriesNumber() { 
        return seriesNumber;
    }
    public void setSeriesNumber(int seriesNumber) { 
        this.seriesNumber = seriesNumber; 
    }
    
    public int getRepetitions() { 
        return repetitions;
    }
    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions; 
    }
    
    public float getWeight() {
        return weight; 
    }
    public void setWeight(float weight) {
        this.weight = weight; 
    }
    
    public int getRestTimeSeconds() {
        return restTimeSeconds; 
    }
    public void setRestTimeSeconds(int restTimeSeconds) {
        this.restTimeSeconds = restTimeSeconds;
    }
    
    public StrengthExercise getStrengthExercise() { 
        return strengthExercise; 
    }
    public void setStrengthExercise(StrengthExercise strengthExercise) {
        this.strengthExercise = strengthExercise;
    }
}

