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

    //getters y setters
    public Time getRecordedTime() {
        return recordedTime;
    }
    public void setRecordedTime(Time recordedTime) {
        this.recordedTime = recordedTime;
    }

    public Float getRecordedVelocity() {
        return recordedVelocity;
    }
    public void setRecordedVelocity(Float recordedVelocity) {
        this.recordedVelocity = recordedVelocity;
    }

    public String getCardioMachine() {
        return cardioMachine;
    }
    public void setCardioMachine(String cardioMachine) {
        this.cardioMachine = cardioMachine;
    }

    public Float getRecordedIncline() {
        return recordedIncline;
    }
    public void setRecordedIncline(Float recordedIncline) {
        this.recordedIncline = recordedIncline;
    }
}
