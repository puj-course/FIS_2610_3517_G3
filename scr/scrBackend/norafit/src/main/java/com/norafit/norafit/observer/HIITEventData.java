package com.norafit.norafit.observer;

public class HIITEventData {

    private final HIITEvent event;
    private final String message;
    private final String exerciseName;
    private final int round;
    private final int totalRounds;
    private final int secondsRemaining;

    public HIITEventData(HIITEvent event, String message, String exerciseName, int round, int totalRounds, int secondsRemaining) {
        this.event = event;
        this.message = message;
        this.exerciseName = exerciseName;
        this.round = round;
        this.totalRounds = totalRounds;
        this.secondsRemaining = secondsRemaining;
    }

    public HIITEventData(HIITEvent event, String message) {
        this(event, message, null, 0, 0, 0);
    }

    public HIITEvent getEvent(){ 
        return event; 
    }
    public String getMessage(){
        return message; 
    }
    public String getExerciseName(){
        return exerciseName; 
    }
    public int getRound(){ 
        return round; 
    }
    public int getTotalRounds(){ 
        return totalRounds; 
    }
    public int getSecondsRemaining(){ 
        return secondsRemaining; 
    }
}
