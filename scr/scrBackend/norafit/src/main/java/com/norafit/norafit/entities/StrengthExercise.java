package com.norafit.norafit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "strength_exercises")
    
public class StrengthExercise extends Exercises {

    @Column(name = "has_weight")
    private boolean hasWeight;

    //constructores
    public StrengthExercise() {
    }

    public StrengthExercise(boolean hasWeight) {
        this.hasWeight = hasWeight;
    }
}
