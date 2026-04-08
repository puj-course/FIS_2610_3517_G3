package com.norafit.norafit.strategy;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.StrengthExercise;
import com.norafit.norafit.entities.StrengthSeries;

public class StrengthTimeStrategy implements TimeCalculationStrategy {

    @Override
    public int calculateTime(Exercise exercise) {

        StrengthExercise strength = (StrengthExercise) exercise;

        int total = 0;

        for (StrengthSeries s : strength.getSeries()) {

            // Tiempo por rep (asumimos 2 segundos por repetición)
            int repsTime = s.getRepetitions() * 2;

            // Tiempo de descanso
            int restTime = s.getRestTimeSeconds();

            total += repsTime + restTime;
        }

        return total;
    }
}
