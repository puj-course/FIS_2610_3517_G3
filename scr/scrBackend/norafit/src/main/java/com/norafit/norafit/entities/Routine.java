package com.norafit.norafit.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "routines")
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private String routineName;
    private float totalTimeSeconds;
    @Column(name = "created_at") 
    private LocalDate created_at;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL)
    private List<Exercises> exercises = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor vacío (Requerido por JPA)
    public Routine() {
        this.createdAt = LocalDate.now();
    }

    public Routine(String routineName, User user) {
        this.routineName = routineName;
        this.user = user;
        this.createdAt = LocalDate.now();
        this.totalTimeSeconds = 0;
    }
   // --- GETTERS Y SETTERS BÁSICOS ---
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public String getRoutineName() {
        return routineName; 
    }
    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }
    public User getUser() { 
        return user; 
    }
    public void setUser(User user) { 
        this.user = user; 
    }
    public LocalDate getCreatedAt() {
    return createdAt;
}
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    public float getTotalTimeSeconds() {
        return totalTimeSeconds;
    }
    public void setTotalTimeSeconds(float totalTimeSeconds) {
        this.totalTimeSeconds = totalTimeSeconds;
    }


    //se debe colocar este método en la entidad (Routine) porque es la que tiene la lista de ejercicios (exercises). Es la que "conoce" a los ejercicios, no al revés.
    public void addExercise(Exercise e) {
    if (this.exercises == null) {
        this.exercises = new ArrayList<>();
    }
    this.exercises.add(e);
    e.setRoutine(this); // se vincula el ejercicio a ESTA rutina
   }
}
