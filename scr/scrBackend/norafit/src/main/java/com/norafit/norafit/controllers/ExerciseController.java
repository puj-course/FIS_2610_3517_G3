package com.norafit.norafit.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.norafit.norafit.entities.StrengthExercise;
import com.norafit.norafit.entities.StrengthSeries;
import com.norafit.norafit.repositories.StrengthExerciseRepository;
import com.norafit.norafit.services.ExerciseService;
import com.norafit.norafit.services.StrengthSeriesService;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final StrengthSeriesService strengthSeriesService;
    private final StrengthExerciseRepository strengthExerciseRepository;

    public ExerciseController(
        ExerciseService exerciseService,
        StrengthSeriesService strengthSeriesService,
        StrengthExerciseRepository strengthExerciseRepository) {
        this.exerciseService = exerciseService;
        this.strengthSeriesService = strengthSeriesService;
        this.strengthExerciseRepository = strengthExerciseRepository;
    }

    // POST /exercises/strength?routineId=1
    @PostMapping("/strength")
    public ResponseEntity<ExerciseResponse> addStrengthExercise(
        @RequestParam Long routineId,
        @RequestBody AddStrengthExerciseRequest request) {

        var exercise = exerciseService.addStrengthExercise(
            routineId, request.name(), request.description(), request.hasWeight());

        return ResponseEntity.ok(new ExerciseResponse(
            exercise.getId(), exercise.getExerciseName(),
            exercise.getDescription(), "STRENGTH"));
    }

    // DELETE /exercises/{id}?routineId=1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(
        @PathVariable Long id, @RequestParam Long routineId) {
        exerciseService.deleteExercise(id, routineId);
        return ResponseEntity.ok().build();
    }

    // PUT /exercises/{id}/rename
    @PutMapping("/{id}/rename")
    public ResponseEntity<ExerciseResponse> renameExercise(
        @PathVariable Long id, @RequestBody RenameExerciseRequest request) {
        var exercise = exerciseService.renameExercise(id, request.newName());
        return ResponseEntity.ok(new ExerciseResponse(
            exercise.getId(), exercise.getExerciseName(),
            exercise.getDescription(), "STRENGTH"));
    }

    // GET /exercises/{id}/series
    @GetMapping("/{id}/series")
    @Transactional(readOnly = true)
    public ResponseEntity<List<SeriesResponse>> getSeries(@PathVariable Long id) {
        List<StrengthSeries> series = strengthSeriesService.getSeriesByStrengthExerciseId(id);
        List<SeriesResponse> response = series.stream()
            .map(s -> new SeriesResponse(
                s.getId(), s.getSeriesNumber(),
                s.getRepetitions(), s.getWeight(), s.getRestTimeSeconds()))
            .toList();
        return ResponseEntity.ok(response);
    }

    // POST /exercises/{id}/series
    @PostMapping("/{id}/series")
    public ResponseEntity<SeriesResponse> addSeries(
        @PathVariable Long id, @RequestBody AddSeriesRequest request) {

        StrengthExercise exercise = strengthExerciseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));

        StrengthSeries series = strengthSeriesService.createSeries(
            exercise, request.seriesNumber(), request.repetitions(),
            request.weight(), request.restTimeSeconds());

        return ResponseEntity.ok(new SeriesResponse(
            series.getId(), series.getSeriesNumber(),
            series.getRepetitions(), series.getWeight(), series.getRestTimeSeconds()));
    }

    // PUT /series/{id}/repetitions
    @PutMapping("/series/{id}/repetitions")
    public ResponseEntity<SeriesResponse> updateRepetitions(
        @PathVariable Long id, @RequestBody UpdateRepetitionsRequest request) {
        StrengthSeries series = strengthSeriesService.updateRepetitions(id, request.repetitions());
        return ResponseEntity.ok(new SeriesResponse(
            series.getId(), series.getSeriesNumber(),
            series.getRepetitions(), series.getWeight(), series.getRestTimeSeconds()));
    }

    // PUT /series/{id}/weight
    @PutMapping("/series/{id}/weight")
    public ResponseEntity<SeriesResponse> updateWeight(
        @PathVariable Long id, @RequestBody UpdateWeightRequest request) {
        StrengthSeries series = strengthSeriesService.updateWeight(id, request.weight());
        return ResponseEntity.ok(new SeriesResponse(
            series.getId(), series.getSeriesNumber(),
            series.getRepetitions(), series.getWeight(), series.getRestTimeSeconds()));
    }
    
    @DeleteMapping("/series/{id}")
    public ResponseEntity<Void> deleteSeries(@PathVariable Long id) {
        strengthSeriesService.deleteSeries(id);
        return ResponseEntity.ok().build();
}

    record AddStrengthExerciseRequest(String name, String description, boolean hasWeight) {}
    record RenameExerciseRequest(String newName) {}
    record AddSeriesRequest(int seriesNumber, int repetitions, float weight, int restTimeSeconds) {}
    record UpdateRepetitionsRequest(int repetitions) {}
    record UpdateWeightRequest(float weight) {}
    record ExerciseResponse(Long id, String name, String description, String type) {}
    record SeriesResponse(Long id, int seriesNumber, int repetitions, float weight, int restTimeSeconds) {}
}
