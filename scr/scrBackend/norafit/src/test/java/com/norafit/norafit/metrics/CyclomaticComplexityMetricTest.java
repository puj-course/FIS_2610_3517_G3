package com.norafit.norafit.metrics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CyclomaticComplexityMetricTest {

    @Test
    void testComplejidadBaja() {
        CyclomaticComplexityMetric metric = new CyclomaticComplexityMetric();
        String codigo = "public void save(String name) { if (name == null) return; }";
        CyclomaticComplexityMetric.ComplexityResult result = metric.analyze(codigo, "save");
        assertTrue(result.getValue() <= 10);
        assertTrue(result.getClassification().contains("BAJO"));
        assertEquals("Sin acción requerida.", result.getRecommendation());
    }

    @Test
    void testComplejidadAlta() {
        CyclomaticComplexityMetric metric = new CyclomaticComplexityMetric();
        String codigo = "if (a) {} if (b) {} if (c) {} if (d) {} if (e) {} " +
                        "while (x) {} while (y) {} while (z) {} " +
                        "for (;;) {} for (;;) {} for (;;) {} " +
                        "case 1: case 2: case 3: case 4: case 5: " +
                        "catch (Exception e) {} catch (Error e) {} " +
                        "&& && && || || ? a : b ? c : d";
        CyclomaticComplexityMetric.ComplexityResult result = metric.analyze(codigo, "metodoComplejo");
        assertTrue(result.getValue() > 20);
        assertTrue(result.getClassification().contains("ALTO"));
    }
}
