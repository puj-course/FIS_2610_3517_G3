package com.norafit.norafit.strategy;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.HIITCardio;

public class HIITTimeStrategy implements TimeCalculationStrategy {

    @Override
public int calculateTime(Exercise exercise) {
    HIITCardio ex = (HIITCardio) exercise;

    int totalWork = ex.getRounds() * ex.getWorkTimeSeconds();
    int totalRest = Math.max(0, ex.getRounds() - 1) * ex.getRestTimeSeconds();

    return totalWork + totalRest;
}
}
