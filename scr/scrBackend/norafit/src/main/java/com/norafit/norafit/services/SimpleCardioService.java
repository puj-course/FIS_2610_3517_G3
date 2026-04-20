package com.norafit.norafit.services;

import org.springframework.stereotype.Service;

import com.norafit.norafit.entities.SimpleCardio;

@Service
public class SimpleCardioService {

    public String getPerformanceMetrics(SimpleCardio cardio) {
        return String.format(
            "Tipo de máquina: %s | Duración: %d min | Intensidad: %s | " +
            "Distancia: %.2f km | Velocidad promedio: %.1f km/h | Inclinación: nivel %d",
            cardio.getMachineType(),
            cardio.getDurationMinutes(),
            cardio.getIntensity(),
            cardio.getDistanceKm(),
            cardio.getAverageSpeed(),
            cardio.getInclineLevel()
        );
    }
}
