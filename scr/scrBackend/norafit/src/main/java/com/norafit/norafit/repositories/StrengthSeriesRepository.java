package com.norafit.norafit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.norafit.norafit.entities.StrengthSeries;

@Repository
public interface StrengthSeriesRepository extends JpaRepository<StrengthSeries, Long> {
    List<StrengthSeries> findByStrengthExerciseId(Long strengthExerciseId);
}
