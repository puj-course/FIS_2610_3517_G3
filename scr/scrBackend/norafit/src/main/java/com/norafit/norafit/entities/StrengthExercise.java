package com.norafit.norafit.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "strength_exercises")
@PrimaryKeyJoinColumn(name = "exercise_id") // Une esta tabla con la de Exercise
public class StrengthExercise extends Exercise {

    private boolean hasWeight;

    // Relación con StrengthSeries (1 a muchos)
    @OneToMany(mappedBy = "strengthExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StrengthSeries> series = new ArrayList<>();

    // Constructor vacío para JPA
    public StrengthExercise() {
        super();
    }

    public List<StrengthSeries> getSeries() {
        return series;
    }

    // --- GETTERS Y SETTERS BÁSICOS ---
    public boolean isHasWeight() { 
        return hasWeight; 
    }
    public void setHasWeight(boolean hasWeight) { 
        this.hasWeight = hasWeight; 
    }
    public void setSeries(List<StrengthSeries> series) { 
        this.series = series; 
    }
}