package com.norafit.norafit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.norafit.norafit.entities.SimpleCardio;
import com.norafit.norafit.services.SimpleCardioService;

class SimpleCardioServiceTest {

    @Test
    void getPerformanceMetrics_ShouldReturnFormattedMetrics() {
        SimpleCardio cardio = new SimpleCardio();
        cardio.setMachineType("Caminadora");
        cardio.setDurationMinutes(30);
        cardio.setIntensity("Alta");
        cardio.setDistanceKm(5.25f);
        cardio.setAverageSpeed(10.5f);
        cardio.setInclineLevel(3);

        SimpleCardioService service = new SimpleCardioService();

        String result = service.getPerformanceMetrics(cardio);

        assertEquals(
            "Tipo de máquina: Caminadora | Duración: 30 min | Intensidad: Alta | Distancia: 5.25 km | Velocidad promedio: 10.5 km/h | Inclinación: nivel 3",
            result
        );
    }
}
