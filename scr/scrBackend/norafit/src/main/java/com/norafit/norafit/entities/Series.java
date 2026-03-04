package com.norafit.norafit.entities;

public class Series {
    private int seriesNumber;
    private int repetitions;
    private int restTimeSeconds;
    private int durationSeconds;
    
     // Constructor vacío 
    public Series() {
    }

    // Constructor con atributos
    public Series(int seriesNumber, int repetitions, int restTimeSeconds) {
        this.seriesNumber = seriesNumber;
        this.repetitions = repetitions;
        this.restTimeSeconds = restTimeSeconds;
    }

    //getters y setters
    public int getSeriesNumber() {
        return seriesNumber;
    }

    public void setSeriesNumber(int seriesNumber) {
        this.seriesNumber = seriesNumber;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getRestTimeSeconds() {
        return restTimeSeconds;
    }

    public void setRestTimeSeconds(int restTimeSeconds) {
    this.restTimeSeconds = restTimeSeconds;
    }
    

    public int getDurationSeconds()
    {
        return durationSeconds;
    }

     public void setDurationSeconds (int durationSeconds) {
         this.durationSeconds = durationSeconds;
     }
    







    

    public void setRestTimeSeconds(int restTimeSeconds) {
        this.restTimeSeconds = restTimeSeconds;
    }
}
