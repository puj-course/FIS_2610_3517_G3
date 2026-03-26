package com.norafit.norafit.entities;

import jakarta.persistence.Entity;

@Entity
public class HIITCardio extends CardioExercise {

    private Integer intervals;

    public HIITCardio() {
        super();
    }

    public HIITCardio(Integer exerciseId, String exerciseName, String description, Integer durationSeconds, Integer intensity, Integer intervals) {
        super(exerciseId, exerciseName, description, durationSeconds, intensity);
        this.intervals = intervals;
    }

    @Override
    public Integer calculateCalories() {
        int base = super.calculateCalories();
        if (intervals == null) return base;
        return base + (intervals * 15);
    }

    public Integer getIntervals() {
        return intervals;
    }

    public void setIntervals(Integer intervals) {
        this.intervals = intervals;
    }
}
