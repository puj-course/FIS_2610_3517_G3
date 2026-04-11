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
import jakarta.persistence.OneToMany;
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
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL)
    private List<Exercise> exercises = new ArrayList<>();
    
    //constructores
    public Routine() {
        this.createdAt = LocalDate.now();
    }

    public Routine(String routineName, User user) {
        this.routineName = routineName;
        this.user = user;
        this.createdAt = LocalDate.now();
        this.totalTimeSeconds = 0;
    }

    //MÉTODO CLAVE (Strategy aplicado) refactorización
    public float calculateTotalTime() {

        float total = 0;
        for (Exercise e : this.exercises) {
            total += e.calculateTime();
        }
        this.totalTimeSeconds = total;
        return total;
    }

    public void addExercise(Exercise e) {
        this.exercises.add(e);
        e.setRoutine(this);
    }

    public void removeExercise(Exercise e) {
        this.exercises.remove(e);
        e.setRoutine(null);
    }

    //getters y setters
    public List<Exercise> getExercises() {
        return exercises;
    }

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
}
