package com.norafit.norafit.factory;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.StrengthExercise;

public class StrengthExerciseFactory implements ExerciseFactory {

    private final boolean hasWeight;

    public StrengthExerciseFactory(boolean hasWeight) {
        this.hasWeight = hasWeight;
    }

    @Override
    public Exercise createExercise(String name, String description) {
        StrengthExercise ex = new StrengthExercise();
        ex.setExerciseName(name);
        ex.setDescription(description);
        ex.setHasWeight(hasWeight);
        return ex;
    }
}
