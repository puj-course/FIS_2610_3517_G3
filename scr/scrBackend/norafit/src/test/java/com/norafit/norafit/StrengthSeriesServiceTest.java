package com.norafit.norafit;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.norafit.norafit.entities.StrengthExercise;
import com.norafit.norafit.entities.StrengthSeries;
import com.norafit.norafit.repositories.StrengthSeriesRepository;
import com.norafit.norafit.services.StrengthSeriesService;

@ExtendWith(MockitoExtension.class)
class StrengthSeriesServiceTest {

    @Mock
    private StrengthSeriesRepository strengthSeriesRepository;

    @InjectMocks
    private StrengthSeriesService strengthSeriesService;

    @Test
    void createSeries_WhenDataIsValid_ShouldSaveSeries() {
        StrengthExercise exercise = new StrengthExercise();

        when(strengthSeriesRepository.save(any(StrengthSeries.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StrengthSeries result = strengthSeriesService.createSeries(exercise, 1, 12, 30, 60);

        assertEquals(1, result.getSeriesNumber());
        assertEquals(12, result.getRepetitions());
        assertEquals(30, result.getWeight());
        assertEquals(60, result.getRestTimeSeconds());
        assertEquals(exercise, result.getStrengthExercise());

        verify(strengthSeriesRepository).save(any(StrengthSeries.class));
    }

    @Test
    void createSeries_WhenExerciseIsNull_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> strengthSeriesService.createSeries(null, 1, 12, 30, 60)
        );

        assertEquals("El ejercicio de fuerza no puede ser nulo.", exception.getMessage());
        verify(strengthSeriesRepository, never()).save(any(StrengthSeries.class));
    }

    @Test
    void createSeries_WhenRepetitionsAreInvalid_ShouldThrowException() {
        StrengthExercise exercise = new StrengthExercise();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> strengthSeriesService.createSeries(exercise, 1, 0, 30, 60)
        );

        assertEquals("Las repeticiones deben ser mayores a 0.", exception.getMessage());
    }

    @Test
    void createSeries_WhenWeightIsNegative_ShouldThrowException() {
        StrengthExercise exercise = new StrengthExercise();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> strengthSeriesService.createSeries(exercise, 1, 12, -1, 60)
        );

        assertEquals("El peso no puede ser negativo.", exception.getMessage());
    }

    @Test
    void getSeriesByStrengthExerciseId_ShouldReturnSeries() {
        when(strengthSeriesRepository.findByStrengthExerciseId(1L))
                .thenReturn(List.of(new StrengthSeries(), new StrengthSeries()));

        List<StrengthSeries> result = strengthSeriesService.getSeriesByStrengthExerciseId(1L);

        assertEquals(2, result.size());
        verify(strengthSeriesRepository).findByStrengthExerciseId(1L);
    }

    @Test
    void updateRepetitions_WhenSeriesExists_ShouldUpdateValue() {
        StrengthSeries series = new StrengthSeries();
        series.setRepetitions(10);

        when(strengthSeriesRepository.findById(1L)).thenReturn(Optional.of(series));
        when(strengthSeriesRepository.save(any(StrengthSeries.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StrengthSeries result = strengthSeriesService.updateRepetitions(1L, 15);

        assertEquals(15, result.getRepetitions());
        verify(strengthSeriesRepository).save(series);
    }

    @Test
    void updateRepetitions_WhenValueIsInvalid_ShouldThrowException() {
        StrengthSeries series = new StrengthSeries();
        series.setRepetitions(10);

        when(strengthSeriesRepository.findById(1L)).thenReturn(Optional.of(series));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> strengthSeriesService.updateRepetitions(1L, 0)
        );

        assertEquals("Las repeticiones deben ser mayores a 0.", exception.getMessage());
        verify(strengthSeriesRepository, never()).save(any(StrengthSeries.class));
    }

    @Test
    void updateWeight_WhenSeriesExists_ShouldUpdateValue() {
        StrengthSeries series = new StrengthSeries();
        series.setWeight(20);

        when(strengthSeriesRepository.findById(1L)).thenReturn(Optional.of(series));
        when(strengthSeriesRepository.save(any(StrengthSeries.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StrengthSeries result = strengthSeriesService.updateWeight(1L, 40);

        assertEquals(40, result.getWeight());
        verify(strengthSeriesRepository).save(series);
    }

    @Test
    void updateWeight_WhenValueIsNegative_ShouldThrowException() {
        StrengthSeries series = new StrengthSeries();
        series.setWeight(20);

        when(strengthSeriesRepository.findById(1L)).thenReturn(Optional.of(series));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> strengthSeriesService.updateWeight(1L, -5)
        );

        assertEquals("El peso no puede ser negativo.", exception.getMessage());
        verify(strengthSeriesRepository, never()).save(any(StrengthSeries.class));
    }
}
