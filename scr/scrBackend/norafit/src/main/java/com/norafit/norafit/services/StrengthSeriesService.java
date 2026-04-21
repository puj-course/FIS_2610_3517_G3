package com.norafit.norafit.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.norafit.norafit.entities.StrengthExercise;
import com.norafit.norafit.entities.StrengthSeries;
import com.norafit.norafit.repositories.StrengthSeriesRepository;

@Service
public class StrengthSeriesService {

    private final StrengthSeriesRepository strengthSeriesRepository;

    public StrengthSeriesService(StrengthSeriesRepository strengthSeriesRepository) {
        this.strengthSeriesRepository = strengthSeriesRepository;
    }

    @Transactional
    public StrengthSeries createSeries(
        StrengthExercise strengthExercise,
        int seriesNumber,
        int repetitions,
        float weight,
        int restTimeSeconds) {

    if (strengthExercise == null) {
        throw new IllegalArgumentException("El ejercicio de fuerza no puede ser nulo.");
    }
    if (seriesNumber <= 0) {
        throw new IllegalArgumentException("El número de serie debe ser mayor a 0.");
    }
    if (repetitions <= 0) {
        throw new IllegalArgumentException("Las repeticiones deben ser mayores a 0.");
    }
    if (weight < 0) {
        throw new IllegalArgumentException("El peso no puede ser negativo.");
    }
    if (restTimeSeconds < 0) {
        throw new IllegalArgumentException("El descanso no puede ser negativo.");
    }

    StrengthSeries series = new StrengthSeries();
    series.setSeriesNumber(seriesNumber);
    series.setRepetitions(repetitions);
    series.setWeight(weight);
    series.setRestTimeSeconds(restTimeSeconds);
    series.setStrengthExercise(strengthExercise);

    return strengthSeriesRepository.save(series);
}

    @Transactional(readOnly = true)
    public List<StrengthSeries> getSeriesByStrengthExerciseId(Long strengthExerciseId) {
        return strengthSeriesRepository.findByStrengthExerciseId(strengthExerciseId);
    }

    @Transactional
    public StrengthSeries updateRepetitions(Long seriesId, int newRepetitions) {
        StrengthSeries series = strengthSeriesRepository.findById(seriesId)
            .orElseThrow(() -> new RuntimeException("No existe la serie de fuerza con ID " + seriesId));

        series.updateRepetitions(newRepetitions);
        return strengthSeriesRepository.save(series);
    }

    @Transactional
    public StrengthSeries updateWeight(Long seriesId, float newWeight) {
        StrengthSeries series = strengthSeriesRepository.findById(seriesId)
            .orElseThrow(() -> new RuntimeException("No existe la serie de fuerza con ID " + seriesId));
        
        series.updateWeight(newWeight);
        return strengthSeriesRepository.save(series);
    }

    @Transactional
    public StrengthSeries saveSeries(StrengthSeries serie) {
        return strengthSeriesRepository.save(serie);
    }
}
