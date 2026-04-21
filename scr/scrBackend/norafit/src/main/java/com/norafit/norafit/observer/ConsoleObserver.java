package com.norafit.norafit.observer;

import org.springframework.stereotype.Component;

@Component
public class ConsoleObserver implements IHIITObserver {

    @Override
    public void onEvent(HIITEventData data) {
      
        switch (data.getEvent()) {
            case EXERCISE_STARTED  -> System.out.println(data.getMessage());
            case EXERCISE_FINISHED -> System.out.println(data.getMessage());
            case ROUND_STARTED     -> System.out.println(data.getMessage());
            case WORK_TICK         -> System.out.println("TRABAJO - " + data.getSecondsRemaining() + "s restantes");
            case REST_TICK         -> System.out.println("DESCANSO - " + data.getSecondsRemaining() + "s restantes");
            case WORK_FINISHED     -> System.out.println(data.getMessage());
            case REST_FINISHED     -> System.out.println(data.getMessage());
        }
    }
}
