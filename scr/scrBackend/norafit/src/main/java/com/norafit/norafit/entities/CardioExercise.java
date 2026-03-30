package com.norafit.norafit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "cardio_exercises")
@Inheritance(strategy = InheritanceType.JOINED)
  
public abstract class CardioExercise extends Exercises {

    @Column(name = "is_hiit")
    private boolean isHIIT;
}
