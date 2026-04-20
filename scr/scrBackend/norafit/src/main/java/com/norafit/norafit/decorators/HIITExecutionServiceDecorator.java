package com.norafit.norafit.decorators;

import java.util.List;

import com.norafit.norafit.entities.HIITCardio;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.services.IHIITExecutionService;

public abstract class HIITExecutionServiceDecorator implements IHIITExecutionService {
    protected final IHIITExecutionService wrappedService;

    public HIITExecutionServiceDecorator(IHIITExecutionService wrappedService) {
        this.wrappedService = wrappedService;
    }

    @Override
    public List<String> executeRoutine(Routine routine, boolean realTime) {
        return wrappedService.executeRoutine(routine, realTime);
    }

    @Override
    public List<String> executeSingleExercise(HIITCardio hiit, boolean realTime) {
        return wrappedService.executeSingleExercise(hiit, realTime);
    }

    @Override
    public boolean routineHasHIIT(Routine routine) {
        return wrappedService.routineHasHIIT(routine);
    }
}
