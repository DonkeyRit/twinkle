package com.github.donkeyrit.twinkle.telemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;

/**
 * Application-wide OpenTelemetry setup.
 *
 * The SDK is configured with a {@link SdkTracerProvider} and no span exporter:
 * the application does not ship traces anywhere, it only relies on the SDK's
 * random, W3C-compliant trace/span id generation to produce correlation ids
 * that get attached to every log line for a given logical operation - see
 * {@link CorrelationContext}.
 */
public final class OpenTelemetryConfig {

    private static final String INSTRUMENTATION_NAME = "com.github.donkeyrit.twinkle";

    private static final OpenTelemetry OPEN_TELEMETRY = buildOpenTelemetry();

    private OpenTelemetryConfig() {
    }

    public static Tracer getTracer() {
        return OPEN_TELEMETRY.getTracer(INSTRUMENTATION_NAME);
    }

    private static OpenTelemetry buildOpenTelemetry() {
        SdkTracerProvider tracerProvider = SdkTracerProvider.builder().build();

        return OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .build();
    }
}
