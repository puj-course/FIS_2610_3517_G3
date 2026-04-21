package com.norafit.norafit.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogObserver implements IHIITObserver {

    private static final Logger log = LoggerFactory.getLogger(LogObserver.class);

    @Override
    public void onEvent(HIITEventData data) {
        switch (data.getEvent()) {
            case ROUTINE_STARTED, ROUTINE_FINISHED,
                 EXERCISE_STARTED, EXERCISE_FINISHED,
                 ROUND_STARTED, WORK_FINISHED, REST_FINISHED ->
                    log.info("[HIIT][{}] {}", data.getEvent(), data.getMessage());

            case WORK_TICK, REST_TICK ->
                    log.debug("[HIIT][{}] ejercicio='{}' ronda={}/{} segundos={}",
                            data.getEvent(),
                            data.getExerciseName(),
                            data.getRound(),
                            data.getTotalRounds(),
                            data.getSecondsRemaining());
        }
    }
}
