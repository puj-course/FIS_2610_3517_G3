package com.norafit.norafit.services;

import java.util.List;

import com.norafit.norafit.entities.HIITCardio;
import com.norafit.norafit.entities.Routine;

public interface IHIITExecutionService {

    List<String> executeRoutine(Routine routine, boolean realTime);

    List<String> executeSingleExercise(HIITCardio hiit, boolean realTime);

    boolean routineHasHIIT(Routine routine);
}
