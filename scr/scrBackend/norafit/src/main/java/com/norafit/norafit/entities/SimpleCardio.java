package com.norafit.norafit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.sql.Time;

@Entity
@Table(name = "simple_cardio")
  
public class SimpleCardio extends CardioExercise {
    @Column(name = "recorded_time")
    private Time recordedTime;

    @Column(name = "recorded_velocity")
    private Float recordedVelocity;

    @Column(name = "cardio_machine")
    private String cardioMachine;

    @Column(name = "recorded_incline")
    private Float recordedIncline;

    //comstructores
    public SimpleCardio() {}

    public SimpleCardio(Time recordedTime, Float recordedVelocity, String cardioMachine, Float recordedIncline) {
        this.recordedTime = recordedTime;
        this.recordedVelocity = recordedVelocity;
        this.cardioMachine = cardioMachine;
        this.recordedIncline = recordedIncline;
    }

}
