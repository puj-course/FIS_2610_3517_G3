package com.norafit.norafit.strategy;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.SimpleCardio;

public class SimpleCardioTimeStrategy implements TimeCalculationStrategy {

    @Override
    public int calculateTime(Exercise exercise) {
        SimpleCardio ex = (SimpleCardio) exercise;

        if (ex.getAverageSpeed() == 0) return 0;

        float hours = ex.getDistanceKm() / ex.getAverageSpeed();

        return (int) (hours * 3600);
    }
}
