package com.norafit.norafit.metrics;

import org.springframework.stereotype.Component;
import java.util.*;
import java.util.logging.Logger;
@Component
public class BusinessRuleViolationMetric {

    private static final Logger logger = Logger.getLogger(BusinessRuleViolationMetric.class.getName());

    public static final int    MAX_ROUTINE_NAME_LENGTH = 100;
    public static final double MAX_WEIGHT_KG           = 500.0;
    public static final double MIN_WEIGHT_KG           = 0.01;
    public static final int    MAX_REPETITIONS         = 999;
    public static final int    MIN_REPETITIONS         = 1;
    public static final int    MAX_ROUTINES_PER_USER   = 50;

    private int totalOperations = 0;
    private int totalViolations = 0;
    private final List<ViolationEvent> violationLog = new ArrayList<>();

    public void validateRoutineName(String name) {
        totalOperations++;
        if (name == null || name.isBlank()) {
            registerViolation("RN-001", "Nombre vacío o nulo");
            throw new IllegalArgumentException("El nombre de la rutina no puede estar vacío.");
        }
        if (name.length() > MAX_ROUTINE_NAME_LENGTH) {
            registerViolation("RN-001", "Nombre excede " + MAX_ROUTINE_NAME_LENGTH + " chars");
            throw new IllegalArgumentException("El nombre no puede superar " + MAX_ROUTINE_NAME_LENGTH + " caracteres.");
        }
    }

    public void validateWeight(double weightKg) {
        totalOperations++;
        if (weightKg < MIN_WEIGHT_KG) {
            registerViolation("RN-002", "Peso inválido: " + weightKg + "kg");
            throw new IllegalArgumentException("El peso debe ser mayor que 0 kg.");
        }
        if (weightKg > MAX_WEIGHT_KG) {
            registerViolation("RN-002", "Peso excede máximo: " + weightKg + "kg");
            throw new IllegalArgumentException("El peso no puede superar " + MAX_WEIGHT_KG + " kg.");
        }
    }

    public void validateRepetitions(int repetitions) {
        totalOperations++;
        if (repetitions < MIN_REPETITIONS || repetitions > MAX_REPETITIONS) {
            registerViolation("RN-003", "Repeticiones fuera de rango: " + repetitions);
            throw new IllegalArgumentException(
                "Las repeticiones deben estar entre " + MIN_REPETITIONS + " y " + MAX_REPETITIONS + ".");
        }
    }

    public void validateEmail(String email) {
        totalOperations++;
        if (email == null || email.isBlank()) {
            registerViolation("RN-004", "Email nulo o vacío");
            throw new IllegalArgumentException("El email no puede estar vacío.");
        }
        boolean valid = email.contains("@") && email.indexOf("@") > 0
                        && email.lastIndexOf(".") > email.indexOf("@");
        if (!valid) {
            registerViolation("RN-004", "Formato inválido: " + email);
            throw new IllegalArgumentException("El formato del email es inválido.");
        }
    }

    public void validateRoutineLimit(int currentRoutineCount) {
        totalOperations++;
        if (currentRoutineCount >= MAX_ROUTINES_PER_USER) {
            registerViolation("RN-005", "Límite alcanzado: " + currentRoutineCount);
            throw new IllegalStateException(
                "Se ha alcanzado el límite de " + MAX_ROUTINES_PER_USER + " rutinas por usuario.");
        }
    }

    public BusinessRuleReport getReport() {
        double brvr = totalOperations > 0 ? (double) totalViolations / totalOperations * 100.0 : 0.0;
        String status = brvr == 0 ? "IDEAL — Sin violaciones" :
                        brvr <= 5  ? "ACEPTABLE" :
                        brvr <= 15 ? "MODERADO — Revisar validaciones" :
                                     "CRÍTICO — Fallas sistemáticas";
        logger.info(String.format("[BusinessRuleMetric] ops=%d | violaciones=%d | BRVR=%.2f%% | %s",
            totalOperations, totalViolations, brvr, status));
        return new BusinessRuleReport(totalOperations, totalViolations, brvr, status,
            Collections.unmodifiableList(violationLog));
    }

    public void reset() {
        totalOperations = 0; totalViolations = 0; violationLog.clear();
    }

    private void registerViolation(String ruleId, String detail) {
        totalViolations++;
        violationLog.add(new ViolationEvent(ruleId, detail, System.currentTimeMillis()));
        logger.warning("[BusinessRuleMetric] VIOLACIÓN " + ruleId + " | " + detail);
    }

    public static class ViolationEvent {
        private final String ruleId, detail;
        private final long   timestamp;
        public ViolationEvent(String ruleId, String detail, long timestamp) {
            this.ruleId = ruleId; this.detail = detail; this.timestamp = timestamp;
        }
        public String getRuleId()    { return ruleId; }
        public String getDetail()    { return detail; }
        public long   getTimestamp() { return timestamp; }
    }

    public static class BusinessRuleReport {
        private final int    totalOperations, totalViolations;
        private final double brvr;
        private final String status;
        private final List<ViolationEvent> events;
        public BusinessRuleReport(int totalOperations, int totalViolations,
                                  double brvr, String status, List<ViolationEvent> events) {
            this.totalOperations = totalOperations; this.totalViolations = totalViolations;
            this.brvr = brvr; this.status = status; this.events = events;
        }
        public int    getTotalOperations() { return totalOperations; }
        public int    getTotalViolations() { return totalViolations; }
        public double getBrvr()            { return brvr; }
        public String getStatus()          { return status; }
        public List<ViolationEvent> getEvents() { return events; }
    }
}
