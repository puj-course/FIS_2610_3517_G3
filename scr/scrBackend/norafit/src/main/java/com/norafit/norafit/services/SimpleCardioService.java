package com.norafit.norafit.services;

import java.sql.Time;
import org.springframework.stereotype.Service;
import com.norafit.norafit.entities.SimpleCardio;

@Service
public class SimpleCardioService {

    //crear SimpleCardio
    public SimpleCardio createSimple(Time recordedTime, Float recordedVelocity, String cardioMachine, Float recordedIncline) {

        if (cardioMachine == null || cardioMachine.isBlank()) {
            throw new IllegalArgumentException("La maquina de cardio es obligatoria");
        }

        SimpleCardio simpleCardio = new SimpleCardio(
            recordedTime,
            recordedVelocity,
            cardioMachine.trim(),
            recordedIncline
        );

        return simpleCardio;
    }

    //registrar metricas
    public void recordMetrics(SimpleCardio simpleCardio, Time recordedTime, Float recordedVelocity, Float recordedIncline) {

        if (simpleCardio == null) {
            throw new IllegalArgumentException("SimpleCardio no puede ser null.");
        }

        if (recordedTime != null) {
            simpleCardio.setRecordedTime(recordedTime);
        }

        if (recordedVelocity != null) {
            simpleCardio.setRecordedVelocity(recordedVelocity);
        }

        if (recordedIncline != null) {
            simpleCardio.setRecordedIncline(recordedIncline);
        }
    }
}
