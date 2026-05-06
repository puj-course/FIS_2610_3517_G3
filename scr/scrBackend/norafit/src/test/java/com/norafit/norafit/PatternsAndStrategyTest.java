package com.norafit.norafit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.norafit.norafit.builder.StrengthSeriesBuilder;
import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.HIITCardio;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.SimpleCardio;
import com.norafit.norafit.entities.StrengthExercise;
import com.norafit.norafit.entities.StrengthSeries;
import com.norafit.norafit.factory.HIITCardioExerciseFactory;
import com.norafit.norafit.factory.SimpleCardioExerciseFactory;
import com.norafit.norafit.factory.StrengthExerciseFactory;

class PatternsAndStrategyTest {

    @Test
    void strengthFactory_ShouldCreateStrengthExercise() {
        StrengthExerciseFactory factory = new StrengthExerciseFactory(true);

        Exercise result = factory.createExercise("Press banca", "Pecho");

        assertTrue(result instanceof StrengthExercise);
        assertEquals("Press banca", result.getExerciseName());
        assertEquals("Pecho", result.getDescription());
        assertTrue(((StrengthExercise) result).isHasWeight());
    }

    @Test
    void hiitFactory_ShouldCreateHIITCardio() {
        HIITCardioExerciseFactory factory = new HIITCardioExerciseFactory(4, 30, 15);

        Exercise result = factory.createExercise("Burpees", "HIIT intenso");

        assertTrue(result instanceof HIITCardio);

        HIITCardio hiit = (HIITCardio) result;
        assertEquals(4, hiit.getRounds());
        assertEquals(30, hiit.getWorkTimeSeconds());
        assertEquals(15, hiit.getRestTimeSeconds());
    }

    @Test
    void simpleCardioFactory_ShouldCreateSimpleCardio() {
        SimpleCardioExerciseFactory factory = new SimpleCardioExerciseFactory(20, "Media");

        Exercise result = factory.createExercise("Caminadora", "Cardio suave");

        assertTrue(result instanceof SimpleCardio);

        SimpleCardio cardio = (SimpleCardio) result;
        assertEquals(20, cardio.getDurationMinutes());
        assertEquals("Media", cardio.getIntensity());
    }

    @Test
    void strengthStrategy_ShouldCalculateTimeFromSeries() {
        StrengthExercise exercise = new StrengthExercise();

        StrengthSeries s1 = new StrengthSeries();
        s1.setRepetitions(10);
        s1.setRestTimeSeconds(60);

        StrengthSeries s2 = new StrengthSeries();
        s2.setRepetitions(8);
        s2.setRestTimeSeconds(45);

        exercise.getSeries().add(s1);
        exercise.getSeries().add(s2);

        int result = exercise.calculateTime();

        assertEquals(141, result);
    }

    @Test
    void hiitStrategy_ShouldCalculateTotalTime() {
        HIITCardio hiit = new HIITCardio();
        hiit.setRounds(3);
        hiit.setWorkTimeSeconds(30);
        hiit.setRestTimeSeconds(15);

        int result = hiit.calculateTime();

        assertEquals(120, result);
    }

    @Test
    void simpleCardioStrategy_WhenAverageSpeedIsZero_ShouldReturnZero() {
        SimpleCardio cardio = new SimpleCardio();
        cardio.setDistanceKm(5);
        cardio.setAverageSpeed(0);

        int result = cardio.calculateTime();

        assertEquals(0, result);
    }

    @Test
    void simpleCardioStrategy_ShouldCalculateTimeInSeconds() {
        SimpleCardio cardio = new SimpleCardio();
        cardio.setDistanceKm(10);
        cardio.setAverageSpeed(10);

        int result = cardio.calculateTime();

        assertEquals(3600, result);
    }

    @Test
    void routineCalculateTotalTime_ShouldSumExerciseTimes() {
        Routine routine = new Routine();

        HIITCardio hiit = new HIITCardio();
        hiit.setRounds(2);
        hiit.setWorkTimeSeconds(20);
        hiit.setRestTimeSeconds(10);

        SimpleCardio cardio = new SimpleCardio();
        cardio.setDistanceKm(5);
        cardio.setAverageSpeed(10);

        routine.addExercise(hiit);
        routine.addExercise(cardio);

        float result = routine.calculateTotalTime();

        assertEquals(1850, result);
    }

    @Test
    void builder_WhenDataIsValid_ShouldBuildStrengthSeries() {
        StrengthExercise exercise = new StrengthExercise();

        StrengthSeries series = new StrengthSeriesBuilder()
                .seriesNumber(1)
                .repetitions(12)
                .weight(30)
                .restTimeSeconds(60)
                .strengthExercise(exercise)
                .build();

        assertEquals(1, series.getSeriesNumber());
        assertEquals(12, series.getRepetitions());
        assertEquals(30, series.getWeight());
        assertEquals(60, series.getRestTimeSeconds());
        assertEquals(exercise, series.getStrengthExercise());
    }

    @Test
    void builder_WhenRepetitionsAreInvalid_ShouldThrowException() {
        StrengthSeriesBuilder builder = new StrengthSeriesBuilder();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> builder.repetitions(0)
        );

        assertEquals("Las repeticiones deben ser mayores a 0.", exception.getMessage());
    }

    @Test
    void builder_WhenExerciseIsMissing_ShouldThrowException() {
        StrengthSeriesBuilder builder = new StrengthSeriesBuilder()
                .seriesNumber(1)
                .repetitions(12)
                .weight(30)
                .restTimeSeconds(60);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                builder::build
        );

        assertEquals("La serie debe estar asociada a un ejercicio de fuerza.", exception.getMessage());
    }
}
