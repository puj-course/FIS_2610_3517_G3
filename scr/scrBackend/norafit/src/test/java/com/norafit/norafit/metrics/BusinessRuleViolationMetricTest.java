@Test
void testNombreVacioEsViolacion() {
    BusinessRuleViolationMetric metric = new BusinessRuleViolationMetric();

    assertThrows(IllegalArgumentException.class, () -> metric.validateRoutineName(""));
    
    BusinessRuleReport report = metric.getReport();
    assertEquals(1, report.getTotalViolations());
    assertEquals(100.0, report.getBrvr(), 0.01); 
    assertTrue(report.getStatus().contains("CRÍTICO"));
}

@Test
void testBRVRIdeal() {
    BusinessRuleViolationMetric metric = new BusinessRuleViolationMetric();
    metric.validateRoutineName("Rutina A");
    metric.validateWeight(80.0);
    metric.validateRepetitions(12);
    metric.validateEmail("user@gmail.com");
    metric.validateRoutineLimit(5);

    BusinessRuleReport report = metric.getReport();
    assertEquals(0, report.getTotalViolations());
    assertEquals(0.0, report.getBrvr(), 0.01);
    assertTrue(report.getStatus().contains("IDEAL"));
}

@Test
void testEmailInvalidoEsDetectado() {
    BusinessRuleViolationMetric metric = new BusinessRuleViolationMetric();
    assertThrows(IllegalArgumentException.class, () -> metric.validateEmail("correo-sin-arroba"));
}
