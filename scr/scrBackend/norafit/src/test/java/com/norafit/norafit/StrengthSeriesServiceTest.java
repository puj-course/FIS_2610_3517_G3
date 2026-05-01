package com.norafit.norafit;

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
    void updateRepetitions_WhenSeriesExists_ShouldUpdateRepetitions() {
        // Arrange
        StrengthSeries series = new StrengthSeries();
        series.setId(1L);
        series.setRepetitions(10);

        when(strengthSeriesRepository.findById(1L)).thenReturn(Optional.of(series));
        when(strengthSeriesRepository.save(any(StrengthSeries.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        StrengthSeries result = strengthSeriesService.updateRepetitions(1L, 12);

        // Assert
        assertEquals(12, result.getRepetitions());
        verify(strengthSeriesRepository).save(series);
    }

    @Test
    void updateWeight_WhenSeriesDoesNotExist_ShouldThrowException() {
        // Arrange
        when(strengthSeriesRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> strengthSeriesService.updateWeight(99L, 40f));

        assertEquals("No existe la serie de fuerza con ID 99", exception.getMessage());
        verify(strengthSeriesRepository, never()).save(any(StrengthSeries.class));
    }
}
