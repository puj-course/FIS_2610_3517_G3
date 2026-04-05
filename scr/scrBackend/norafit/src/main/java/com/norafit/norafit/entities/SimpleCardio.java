package com.norafit.norafit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "simple_cardio")
@PrimaryKeyJoinColumn(name = "cardio_exercise_id")
public class SimpleCardio extends CardioExercise {

    private float distanceKm;
    private float averageSpeed;
    private int inclineLevel;

    public SimpleCardio() {
        super();
    }

    // Getters y Setters
    public float getDistanceKm() {
         return distanceKm;
         }
    public void setDistanceKm(float distanceKm) { 
        this.distanceKm = distanceKm; 
    }
    public float getAverageSpeed() { 
        return averageSpeed; 
    }
    public void setAverageSpeed(float averageSpeed) { 
        this.averageSpeed = averageSpeed; 
    }
    public int getInclineLevel() { 
        return inclineLevel; 
    }
    public void setInclineLevel(int inclineLevel) { 
        this.inclineLevel = inclineLevel; 
    }
}