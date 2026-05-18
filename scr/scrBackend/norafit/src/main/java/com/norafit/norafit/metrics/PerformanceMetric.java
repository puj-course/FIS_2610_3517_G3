package com.norafit.norafit.metrics;

import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Component
public class PerformanceMetric {

    private static final Logger logger = Logger.getLogger(PerformanceMetric.class.getName());

    public static final long LATENCY_EXCELLENT_MS  = 50L;
    public static final long LATENCY_ACCEPTABLE_MS = 200L;

    private final Map<String, List<Long>>    latencyRecords  = new ConcurrentHashMap<>();
    private final Map<String, AtomicLong>    invocationCount = new ConcurrentHashMap<>();
    private final Map<String, Long>          startTimestamps = new ConcurrentHashMap<>();

    public void startOperation(String operationName) {###
        startTimestamps.put(operationName, System.nanoTime()); ###
        invocationCount.computeIfAbsent(operationName, k -> new AtomicLong(0)).incrementAndGet();
        latencyRecords.computeIfAbsent(operationName, k -> Collections.synchronizedList(new ArrayList<>()));
    }

    public long endOperation(String operationName) {
        Long startNano = startTimestamps.get(operationName);
        if (startNano == null)
            throw new IllegalStateException("No se encontró inicio de operación para: " + operationName);
        long elapsedMs = (System.nanoTime() - startNano) / 1_000_000L;###
        latencyRecords.get(operationName).add(elapsedMs);
        logger.info(String.format("[PerformanceMetric] %s → %dms", operationName, elapsedMs));
        return elapsedMs;
    }

    public PerformanceReport getReport(String operationName) {
        List<Long> records = latencyRecords.getOrDefault(operationName, Collections.emptyList());
        if (records.isEmpty())
            throw new IllegalStateException("No hay registros de latencia para: " + operationName);

        List<Long> sorted = new ArrayList<>(records);###
        Collections.sort(sorted);
        long   min        = sorted.get(0);
        long   max        = sorted.get(sorted.size() - 1);
        double average    = sorted.stream().mapToLong(Long::longValue).average().orElse(0);
        long   p95        = sorted.get((int) Math.ceil(sorted.size() * 0.95) - 1);
        long   total      = sorted.stream().mapToLong(Long::longValue).sum();
        double throughput = total > 0 ? (sorted.size() * 1000.0) / total : 0;
        String status     = average < LATENCY_EXCELLENT_MS  ? "EXCELENTE (<50ms)" :
                            average < LATENCY_ACCEPTABLE_MS ? "ACEPTABLE (50-200ms)" :
                                                              "REQUIERE OPTIMIZACIÓN (>200ms)";
        PerformanceReport report = new PerformanceReport(
            operationName, sorted.size(), min, max, average, p95, throughput, status);
        logger.info(String.format("[PerformanceMetric] REPORTE %s | avg=%.2fms | P95=%dms | %.2f ops/s | %s",
            operationName, average, p95, throughput, status));
        return report;
    }

    public Map<String, PerformanceReport> getAllReports() {
        Map<String, PerformanceReport> all = new LinkedHashMap<>();
        for (String op : latencyRecords.keySet()) all.put(op, getReport(op));
        return all;
    }

    public void reset(String operationName) {
        latencyRecords.remove(operationName);
        invocationCount.remove(operationName);
        startTimestamps.remove(operationName);
    }

    public static class PerformanceReport {
        private final String operationName;
        private final int    sampleCount;
        private final long   minMs, maxMs, p95Ms;
        private final double averageMs, throughputOpsPerSec;
        private final String status;

        public PerformanceReport(String operationName, int sampleCount, long minMs, long maxMs,
                                 double averageMs, long p95Ms, double throughputOpsPerSec, String status) {
            this.operationName = operationName; this.sampleCount = sampleCount;
            this.minMs = minMs; this.maxMs = maxMs; this.averageMs = averageMs;
            this.p95Ms = p95Ms; this.throughputOpsPerSec = throughputOpsPerSec; this.status = status;
        }
        public String getOperationName()       { return operationName; }
        public int    getSampleCount()         { return sampleCount; }
        public long   getMinMs()               { return minMs; }
        public long   getMaxMs()               { return maxMs; }
        public double getAverageMs()           { return averageMs; }
        public long   getP95Ms()               { return p95Ms; }
        public double getThroughputOpsPerSec() { return throughputOpsPerSec; }
        public String getStatus()              { return status; }
    }
}
