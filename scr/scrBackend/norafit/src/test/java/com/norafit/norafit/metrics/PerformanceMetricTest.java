@Test
void testLatenciaExcelente() throws InterruptedException {
    PerformanceMetric metric = new PerformanceMetric();

    metric.startOperation("guardarRutina");
    Thread.sleep(10);
    long elapsed = metric.endOperation("guardarRutina");

    PerformanceReport report = metric.getReport("guardarRutina");

    assertTrue(elapsed >= 10);
    assertEquals(1, report.getSampleCount());
    assertTrue(report.getStatus().contains("EXCELENTE"));
}

@Test
void testLatenciaRequiereOptimizacion() throws InterruptedException {
    PerformanceMetric metric = new PerformanceMetric();

    metric.startOperation("consultaLenta");
    Thread.sleep(250);
    metric.endOperation("consultaLenta");

    PerformanceReport report = metric.getReport("consultaLenta");

    assertTrue(report.getStatus().contains("REQUIERE OPTIMIZACIÓN"));
}

@Test
void testEndSinStartLanzaExcepcion() {
    PerformanceMetric metric = new PerformanceMetric();
    assertThrows(IllegalStateException.class, () -> metric.endOperation("inexistente"));
}
