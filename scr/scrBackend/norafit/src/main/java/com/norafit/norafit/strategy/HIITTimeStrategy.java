package com.norafit.norafit.strategy;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.HIITCardio;

public class HIITTimeStrategy implements TimeCalculationStrategy {

    @Override
    public int calculateTime(Exercise exercise) {
        HIITCardio ex = (HIITCardio) exercise;

        return ex.getRounds() *
              (ex.getWorkTimeSeconds() + ex.getRestTimeSeconds());
    }
}
