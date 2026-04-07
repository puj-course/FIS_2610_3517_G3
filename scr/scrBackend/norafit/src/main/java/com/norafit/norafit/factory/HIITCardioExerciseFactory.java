package com.norafit.norafit.factory;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.HIITCardio;

public class HIITCardioExerciseFactory implements ExerciseFactory {

    private final int rounds;
    private final int workTimeSeconds;
    private final int restTimeSeconds;

    public HIITCardioExerciseFactory(int rounds, int workTimeSeconds, int restTimeSeconds) {
        this.rounds = rounds;
        this.workTimeSeconds = workTimeSeconds;
        this.restTimeSeconds = restTimeSeconds;
    }

    @Override
    public Exercise createExercise(String name, String description) {
        HIITCardio ex = new HIITCardio();
        ex.setExerciseName(name);
        ex.setDescription(description);
        ex.setRounds(rounds);
        ex.setWorkTimeSeconds(workTimeSeconds);
        ex.setRestTimeSeconds(restTimeSeconds);
        return ex;
    }
}