package com.norafit.norafit.entities;

import java.util.ArrayList;
import java.util.List;

import com.norafit.norafit.strategy.StrengthTimeStrategy;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "strength_exercises")
@PrimaryKeyJoinColumn(name = "exercise_id")
public class StrengthExercise extends Exercise {

    private boolean hasWeight;

    @OneToMany(mappedBy = "strengthExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StrengthSeries> series = new ArrayList<>();

    public StrengthExercise() {
        super();
        this.strategy = new StrengthTimeStrategy();
    }

    public List<StrengthSeries> getSeries() {
        return series;
    }

    public void setSeries(List<StrengthSeries> series) {
        this.series = series;
    }

    public boolean isHasWeight() {
        return hasWeight;
    }

    public void setHasWeight(boolean hasWeight) {
        this.hasWeight = hasWeight;
    }
}
