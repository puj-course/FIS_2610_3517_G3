package com.norafit.norafit.factory;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.SimpleCardio;

public class SimpleCardioExerciseFactory implements ExerciseFactory {

    private final int durationMinutes;
    private final String intensity;

    public SimpleCardioExerciseFactory(int durationMinutes, String intensity) {
        this.durationMinutes = durationMinutes;
        this.intensity = intensity;
    }

    @Override
    public Exercise createExercise(String name, String description) {
        SimpleCardio ex = new SimpleCardio();
        ex.setExerciseName(name);
        ex.setDescription(description);
        ex.setDurationMinutes(durationMinutes);
        ex.setIntensity(intensity);
        return ex;
    }
}