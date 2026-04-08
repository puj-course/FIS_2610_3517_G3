package com.norafit.norafit.strategy;

import com.norafit.norafit.entities.Exercise;

public interface TimeCalculationStrategy {
    int calculateTime(Exercise exercise);
}
