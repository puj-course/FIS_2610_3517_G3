package com.norafit.norafit.metrics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PerformanceMetricTest {

    @Test
    void testLatenciaExcelente() throws InterruptedException {
        PerformanceMetric metric = new PerformanceMetric();
        metric.startOperation("guardarRutina");
        Thread.sleep(10);
        long elapsed = metric.endOperation("guardarRutina");
        PerformanceMetric.PerformanceReport report = metric.getReport("guardarRutina");
        assertTrue(elapsed >= 0);
        assertEquals(1, report.getSampleCount());
        assertTrue(report.getStatus().contains("EXCELENTE") || report.getStatus().contains("ACEPTABLE"));
    }

    @Test
    void testMultiplesMediciones() throws InterruptedException {
        PerformanceMetric metric = new PerformanceMetric();
        for (int i = 0; i < 3; i++) {
            metric.startOperation("op");
            Thread.sleep(5);
            metric.endOperation("op");
        }
        PerformanceMetric.PerformanceReport report = metric.getReport("op");
        assertEquals(3, report.getSampleCount());
        assertTrue(report.getAverageMs() >= 0);
    }

    @Test
    void testEndSinStartLanzaExcepcion() {
        PerformanceMetric metric = new PerformanceMetric();
        assertThrows(IllegalStateException.class, () -> metric.endOperation("inexistente"));
    }
}
