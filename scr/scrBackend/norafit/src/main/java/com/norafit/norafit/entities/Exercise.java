package com.norafit.norafit.entities;

import com.norafit.norafit.strategy.TimeCalculationStrategy;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "exercises")
@Inheritance(strategy = InheritanceType.JOINED)
    
public abstract class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exerciseName;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    // Strategy no persistente
    @Transient
    protected TimeCalculationStrategy strategy;

    public Exercise() {}
    
    public int calculateTime() {
        if (strategy == null) return 0;
        return strategy.calculateTime(this);
    }

    public void updateBaseInfo(String name, String desc) {
        this.exerciseName = name;
        this.description = desc;
    }

    //getters y setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) {
        this.id = id; 
    }

    public String getExerciseName() { 
        return exerciseName; 
    }
    public void setExerciseName(String exerciseName) { 
        this.exerciseName = exerciseName; 
    }

    public String getDescription() { 
        return description; 
    }
    public void setDescription(String description) { 
        this.description = description; 
    }

    public Routine getRoutine() {
        return routine; 
    }
    public void setRoutine(Routine routine) { 
        this.routine = routine; 
    }
}
