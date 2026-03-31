package com.norafit.norafit.services;

import org.springframework.stereotype.Service;

import com.norafit.norafit.entities.Exercises;

@Service
public class CardioExerciseService {

    public Exercises createCardio(
            String name,
            String description,
            Integer durationSeconds,
            boolean isHIIT
    ) {
        Exercises cardio = new Exercises();
        cardio.setExerciseName(name);
        cardio.setDescription(description);
        cardio.setDurationSeconds(durationSeconds);

        if (isHIIT) {
            cardio.setDescription(description + " - HIIT");
        }

        return cardio;
    }
}
