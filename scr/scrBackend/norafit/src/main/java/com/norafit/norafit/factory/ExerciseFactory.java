package com.norafit.norafit.factory;

import com.norafit.norafit.entities.Exercise;

public interface ExerciseFactory {
    Exercise createExercise(String name, String description);
}
