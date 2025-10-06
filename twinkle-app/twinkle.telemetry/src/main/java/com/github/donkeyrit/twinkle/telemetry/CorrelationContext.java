package com.github.donkeyrit.twinkle.telemetry;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.slf4j.MDC;

/**
 * Starts an OpenTelemetry span for a logical operation (application startup,
 * a login attempt, ...) and publishes its trace id as the {@code correlationId}
 * MDC entry, so every log statement emitted while the operation is in
 * progress - on this thread, including from nested operations - carries the
 * same correlation id.
 *
 * Usage:
 * <pre>
 * try (CorrelationContext correlation = CorrelationContext.start("user-login")) {
 *     ...
 * }
 * </pre>
 */
public final class CorrelationContext implements AutoCloseable {

    public static final String MDC_KEY = "correlationId";

    private final Span span;
    private final Scope spanScope;
    private final String previousCorrelationId;

    private CorrelationContext(Span span, Scope spanScope, String previousCorrelationId) {
        this.span = span;
        this.spanScope = spanScope;
        this.previousCorrelationId = previousCorrelationId;
    }

    public static CorrelationContext start(String operationName) {
        Tracer tracer = OpenTelemetryConfig.getTracer();
        Span span = tracer.spanBuilder(operationName).startSpan();
        Scope spanScope = span.makeCurrent();

        String previousCorrelationId = MDC.get(MDC_KEY);
        MDC.put(MDC_KEY, span.getSpanContext().getTraceId());

        return new CorrelationContext(span, spanScope, previousCorrelationId);
    }

    public String correlationId() {
        return span.getSpanContext().getTraceId();
    }

    @Override
    public void close() {
        if (previousCorrelationId != null) {
            MDC.put(MDC_KEY, previousCorrelationId);
        } else {
            MDC.remove(MDC_KEY);
        }

        spanScope.close();
        span.end();
    }
}
