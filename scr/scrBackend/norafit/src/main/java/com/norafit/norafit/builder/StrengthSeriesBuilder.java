package com.norafit.norafit.builder;

import com.norafit.norafit.entities.StrengthExercise;
import com.norafit.norafit.entities.StrengthSeries;

public class StrengthSeriesBuilder {

    private int seriesNumber;
    private int repetitions;
    private float weight = 0;
    private int restTimeSeconds;
    private StrengthExercise strengthExercise;

    public StrengthSeriesBuilder seriesNumber(int seriesNumber) {
        this.seriesNumber = seriesNumber;
        return this;
    }

    public StrengthSeriesBuilder repetitions(int repetitions) {
        if (repetitions <= 0) {
            throw new IllegalArgumentException("Las repeticiones deben ser mayores a 0.");
        }
        this.repetitions = repetitions;
        return this;
    }

    public StrengthSeriesBuilder weight(float weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo.");
        }
        this.weight = weight;
        return this;
    }

    public StrengthSeriesBuilder restTimeSeconds(int restTimeSeconds) {
        if (restTimeSeconds < 0) {
            throw new IllegalArgumentException("El tiempo de descanso no puede ser negativo.");
        }
        this.restTimeSeconds = restTimeSeconds;
        return this;
    }

    public StrengthSeriesBuilder strengthExercise(StrengthExercise strengthExercise) {
        this.strengthExercise = strengthExercise;
        return this;
    }

    public StrengthSeries build() {
        if (seriesNumber <= 0) {
            throw new IllegalStateException("El número de serie debe ser mayor a 0.");
        }
        if (repetitions <= 0) {
            throw new IllegalStateException("Debes definir las repeticiones antes de construir la serie.");
        }
        if (strengthExercise == null) {
            throw new IllegalStateException("La serie debe estar asociada a un ejercicio de fuerza.");
        }

        StrengthSeries serie = new StrengthSeries();
        serie.setSeriesNumber(seriesNumber);
        serie.setRepetitions(repetitions);
        serie.setWeight(weight);
        serie.setRestTimeSeconds(restTimeSeconds);
        serie.setStrengthExercise(strengthExercise);
        return serie;
    }
}
