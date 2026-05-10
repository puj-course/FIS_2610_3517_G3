package com.norafit.norafit.metrics;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.logging.Logger;
@Component
public class CyclomaticComplexityMetric {

    private static final Logger logger = Logger.getLogger(CyclomaticComplexityMetric.class.getName());

    public static final int CC_LOW_THRESHOLD    = 10;
    public static final int CC_MEDIUM_THRESHOLD = 20;

    public ComplexityResult analyze(String sourceCode, String methodName) {
        if (sourceCode == null || sourceCode.isBlank())
            throw new IllegalArgumentException("El código fuente no puede estar vacío.");

        int cc = 1;
        cc += countOccurrences(sourceCode, "if ");
        cc += countOccurrences(sourceCode, "else if ");
        cc += countOccurrences(sourceCode, "} else {");
        cc += countOccurrences(sourceCode, "while ");
        cc += countOccurrences(sourceCode, "for ");
        cc += countOccurrences(sourceCode, "case ");
        cc += countOccurrences(sourceCode, "catch ");
        cc += countOccurrences(sourceCode, "&&");
        cc += countOccurrences(sourceCode, "||");
        cc += countOccurrences(sourceCode, "? ");

        String classification = classify(cc);
        String recommendation = recommend(cc);
        ComplexityResult result = new ComplexityResult(methodName, cc, classification, recommendation);
        logger.info(String.format("[CyclomaticComplexityMetric] %s → CC=%d | %s",
            methodName, cc, classification));
        return result;
    }

    public Map<String, ComplexityResult> analyzeAll(Map<String, String> methods) {
        Map<String, ComplexityResult> results = new LinkedHashMap<>();
        int total = 0;
        for (Map.Entry<String, String> e : methods.entrySet()) {
            ComplexityResult r = analyze(e.getValue(), e.getKey());
            results.put(e.getKey(), r);
            total += r.getValue();
        }
        double avg = (double) total / methods.size();
        logger.info(String.format("[CyclomaticComplexityMetric] CC promedio del módulo: %.2f", avg));
        return results;
    }

    private int countOccurrences(String source, String token) {
        int count = 0, idx = 0;
        while ((idx = source.indexOf(token, idx)) != -1) { count++; idx += token.length(); }
        return count;
    }

    private String classify(int cc) {
        if (cc <= CC_LOW_THRESHOLD)    return "BAJO (1-10) — Código bien estructurado";
        if (cc <= CC_MEDIUM_THRESHOLD) return "MODERADO (11-20) — Considerar refactorización";
        return "ALTO (>20) — Refactorización recomendada";
    }

    private String recommend(int cc) {
        if (cc <= CC_LOW_THRESHOLD)    return "Sin acción requerida.";
        if (cc <= CC_MEDIUM_THRESHOLD) return "Aplicar Extract Method para reducir ramificaciones.";
        return "Dividir el método en responsabilidades más pequeñas. Revisar Strategy Pattern.";
    }

    public static class ComplexityResult {
        private final String methodName;
        private final int    value;
        private final String classification;
        private final String recommendation;

        public ComplexityResult(String methodName, int value, String classification, String recommendation) {
            this.methodName = methodName; this.value = value;
            this.classification = classification; this.recommendation = recommendation;
        }
        public String getMethodName()     { return methodName; }
        public int    getValue()          { return value; }
        public String getClassification() { return classification; }
        public String getRecommendation() { return recommendation; }
    }
}
