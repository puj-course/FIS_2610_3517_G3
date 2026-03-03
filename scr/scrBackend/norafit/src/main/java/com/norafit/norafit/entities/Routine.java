package com.norafit.norafit.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "routines")
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    private Integer routineId;
    private String routineName;
    private float totalTimeSeconds;
    private LocalDate created_at;
    @Transient
    private List<Exercises> exercises = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor vacio 
    public Routine() {
    }

    //  Constructor sin lista
    public Routine(int routineId, String routineName, float totalTimeSeconds, LocalDate created_at) {
        this.routineId = routineId;
        this.routineName = routineName;
        this.totalTimeSeconds = totalTimeSeconds;
        this.created_at = created_at;
    }

    //  Constructor completo
    public Routine(int routineId, String routineName, float totalTimeSeconds, LocalDate created_at, List<Exercises> exercises) {
        this.routineId = routineId;
        this.routineName = routineName;
        this.totalTimeSeconds = totalTimeSeconds;
        this.created_at = created_at;
        this.exercises = exercises;
    }

    // getters y setters
     public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public float getTotalTimeSeconds() {
        return totalTimeSeconds;
    }

    public void setTotalTimeSeconds(float totalTimeSeconds) {
        this.totalTimeSeconds = totalTimeSeconds;
    }

    public LocalDate getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.created_at = createdAt;
    }

    public List<Exercises> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercises> exercises) {
        this.exercises = exercises;
    }
}
