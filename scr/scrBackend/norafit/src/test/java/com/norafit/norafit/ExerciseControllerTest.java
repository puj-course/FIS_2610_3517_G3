package com.norafit.norafit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.norafit.norafit.controllers.ExerciseController;
import com.norafit.norafit.entities.StrengthExercise;
import com.norafit.norafit.entities.StrengthSeries;
import com.norafit.norafit.repositories.StrengthExerciseRepository;
import com.norafit.norafit.services.ExerciseService;
import com.norafit.norafit.services.StrengthSeriesService;

class ExerciseControllerTest {

    @Test
    void deleteExercise_ShouldCallService() {
        ExerciseService exerciseService = mock(ExerciseService.class);
        StrengthSeriesService seriesService = mock(StrengthSeriesService.class);
        StrengthExerciseRepository repository = mock(StrengthExerciseRepository.class);

        ExerciseController controller = new ExerciseController(exerciseService, seriesService, repository);

        ResponseEntity<Void> response = controller.deleteExercise(1L, 10L);

        assertEquals(200, response.getStatusCode().value());
        verify(exerciseService).deleteExercise(1L, 10L);
    }

    @Test
    void getSeries_ShouldReturnSeriesList() {
        ExerciseService exerciseService = mock(ExerciseService.class);
        StrengthSeriesService seriesService = mock(StrengthSeriesService.class);
        StrengthExerciseRepository repository = mock(StrengthExerciseRepository.class);

        StrengthSeries series = new StrengthSeries();
        series.setId(1L);
        series.setSeriesNumber(1);
        series.setRepetitions(12);
        series.setWeight(50f);
        series.setRestTimeSeconds(60);

        when(seriesService.getSeriesByStrengthExerciseId(5L))
                .thenReturn(List.of(series));

        ExerciseController controller = new ExerciseController(exerciseService, seriesService, repository);

        ResponseEntity<?> response = controller.getSeries(5L);

        assertEquals(200, response.getStatusCode().value());
        verify(seriesService).getSeriesByStrengthExerciseId(5L);
    }

    @Test
    void addSeries_WhenExerciseExists_ShouldCreateSeries() {
        ExerciseService exerciseService = mock(ExerciseService.class);
        StrengthSeriesService seriesService = mock(StrengthSeriesService.class);
        StrengthExerciseRepository repository = mock(StrengthExerciseRepository.class);

        StrengthExercise exercise = new StrengthExercise();
        exercise.setId(5L);

        StrengthSeries series = new StrengthSeries();
        series.setId(1L);
        series.setSeriesNumber(1);
        series.setRepetitions(10);
        series.setWeight(40f);
        series.setRestTimeSeconds(30);

        when(repository.findById(5L)).thenReturn(Optional.of(exercise));
        when(seriesService.createSeries(exercise, 1, 10, 40f, 30))
                .thenReturn(series);

        ExerciseController controller = new ExerciseController(exerciseService, seriesService, repository);

        var request = new ExerciseController.AddSeriesRequest(1, 10, 40f, 30);
        ResponseEntity<?> response = controller.addSeries(5L, request);

        assertEquals(200, response.getStatusCode().value());
        verify(repository).findById(5L);
        verify(seriesService).createSeries(exercise, 1, 10, 40f, 30);
    }

    @Test
    void addSeries_WhenExerciseDoesNotExist_ShouldThrowException() {
        ExerciseService exerciseService = mock(ExerciseService.class);
        StrengthSeriesService seriesService = mock(StrengthSeriesService.class);
        StrengthExerciseRepository repository = mock(StrengthExerciseRepository.class);

        when(repository.findById(5L)).thenReturn(Optional.empty());

        ExerciseController controller = new ExerciseController(exerciseService, seriesService, repository);

        var request = new ExerciseController.AddSeriesRequest(1, 10, 40f, 30);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> controller.addSeries(5L, request)
        );

        assertEquals("Ejercicio no encontrado", exception.getMessage());
        verify(seriesService, never()).createSeries(any(), anyInt(), anyInt(), anyFloat(), anyInt());
    }

    @Test
    void updateRepetitions_ShouldReturnUpdatedSeries() {
        ExerciseService exerciseService = mock(ExerciseService.class);
        StrengthSeriesService seriesService = mock(StrengthSeriesService.class);
        StrengthExerciseRepository repository = mock(StrengthExerciseRepository.class);

        StrengthSeries series = new StrengthSeries();
        series.setId(1L);
        series.setSeriesNumber(1);
        series.setRepetitions(15);
        series.setWeight(50f);
        series.setRestTimeSeconds(45);

        when(seriesService.updateRepetitions(1L, 15)).thenReturn(series);

        ExerciseController controller = new ExerciseController(exerciseService, seriesService, repository);

        var request = new ExerciseController.UpdateRepetitionsRequest(15);
        ResponseEntity<?> response = controller.updateRepetitions(1L, request);

        assertEquals(200, response.getStatusCode().value());
        verify(seriesService).updateRepetitions(1L, 15);
    }

    @Test
    void updateWeight_ShouldReturnUpdatedSeries() {
        ExerciseService exerciseService = mock(ExerciseService.class);
        StrengthSeriesService seriesService = mock(StrengthSeriesService.class);
        StrengthExerciseRepository repository = mock(StrengthExerciseRepository.class);

        StrengthSeries series = new StrengthSeries();
        series.setId(1L);
        series.setSeriesNumber(1);
        series.setRepetitions(10);
        series.setWeight(60f);
        series.setRestTimeSeconds(45);

        when(seriesService.updateWeight(1L, 60f)).thenReturn(series);

        ExerciseController controller = new ExerciseController(exerciseService, seriesService, repository);

        var request = new ExerciseController.UpdateWeightRequest(60f);
        ResponseEntity<?> response = controller.updateWeight(1L, request);

        assertEquals(200, response.getStatusCode().value());
        verify(seriesService).updateWeight(1L, 60f);
    }
}
