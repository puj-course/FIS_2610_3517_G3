@Test
void testComplejidadBaja() {
    CyclomaticComplexityMetric metric = new CyclomaticComplexityMetric();
    
    String codigoSimple = "public void save(String name) { if (name == null) return; }";
    
    ComplexityResult result = metric.analyze(codigoSimple, "save");
    assertTrue(result.getValue() <= 10);
    assertTrue(result.getClassification().contains("BAJO"));
    assertEquals("Sin acción requerida.", result.getRecommendation());
}

@Test
void testComplejidadAlta() {
    CyclomaticComplexityMetric metric = new CyclomaticComplexityMetric();
    String codigoComplejo = "if () {} if () {} while () {} for () {} " +
                            "case: case: catch () {} && || ? ";

    ComplexityResult result = metric.analyze(codigoComplejo, "metodoComplejo");

    assertTrue(result.getValue() > 20);
    assertTrue(result.getClassification().contains("ALTO"));
}
