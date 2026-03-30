package com.norafit.norafit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "hiit_cardio")
  
public class HIITCardio extends CardioExercise {

    @Column(name = "hiit_intensity")
    private String hiitIntensity;

    @Column(name = "rounds")
    private int rounds;

    @Column(name = "work_time")
    private int workTime;

    @Column(name = "rest_time")
    private int restTime;

    //constructores
    public HIITCardio() {}

    public HIITCardio(String hiitIntensity, int rounds, int workTime, int restTime) {
        this.hiitIntensity = hiitIntensity;
        this.rounds = rounds;
        this.workTime = workTime;
        this.restTime = restTime;
    }

}
