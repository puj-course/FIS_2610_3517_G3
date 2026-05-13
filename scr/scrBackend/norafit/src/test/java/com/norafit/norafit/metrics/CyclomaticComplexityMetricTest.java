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
        String codigo = "if (a) {} if (b) {} while (c) {} for (;;) {} " +
                        "case 1: case 2: catch (Exception e) {} && || ? x : y " +
                        "if (d) {} if (e) {} while (f) {} for (;;) {} case 3: && ||";
        CyclomaticComplexityMetric.ComplexityResult result = metric.analyze(codigo, "metodoComplejo");
        assertTrue(result.getValue() > 20);
        assertTrue(result.getClassification().contains("ALTO"));
    }
}
