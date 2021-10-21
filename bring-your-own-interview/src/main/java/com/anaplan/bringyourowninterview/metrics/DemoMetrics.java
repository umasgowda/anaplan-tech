package com.anaplan.bringyourowninterview.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * To be able to monitor custom metrics we need to import MeterRegistry from the Micrometer library and inject it into our class.
 * This gives us the possibility to use counters, gauges, timers and more.
 * DemoMetrics has a custom Counter and Gauge, which will get updated every second through our DemoMetricsScheduler class.
 * The counter gets incremented by one, and the gauge will get a random number between 1 and 100.
 */
@Component
public class DemoMetrics {

    private final Counter demoCounter;
    private final AtomicInteger demoGauge;


    public DemoMetrics(MeterRegistry meterRegistry) {
        this.demoCounter = meterRegistry.counter("demo_counter");
        this.demoGauge = meterRegistry.gauge("demo_gauge", new AtomicInteger(0));
    }

    public void getRandomMetricsData() {
        demoGauge.set(getRandomNumberInRange(0, 100));
        demoCounter.increment();
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
