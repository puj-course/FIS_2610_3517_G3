package com.norafit.norafit.strategy;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.StrengthExercise;
import com.norafit.norafit.entities.StrengthSeries;

public class StrengthTimeStrategy implements TimeCalculationStrategy {

    @Override
    public int calculateTime(Exercise exercise) {
        StrengthExercise ex = (StrengthExercise) exercise;

        int total = 0;

        for (StrengthSeries s : ex.getSeries()) {
            total += s.getRepetitions() * s.getSecondsPerRep();
        }

        return total;
    }
}
