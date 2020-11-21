package com.hub.eventgenerator.service;

import com.hub.eventgenerator.model.RandomAudioEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that represents RandomAudioEvent generation.
 */
public class EventGenerator {
    public static final Logger logger = LogManager.getLogger(EventGenerator.class);

    private final ApiService apiService;
    private final ScheduledExecutorService scheduledExecutorService;

    private AtomicInteger delay;
    private boolean isRandomDelay;
    private int randDelayLeftBound;
    private int randDelayRightBound;

    private EventGenerator() {
        apiService = new ApiService();
        scheduledExecutorService = Executors.newScheduledThreadPool(4);
        randDelayLeftBound = 5;
        randDelayRightBound = 60;
    }

    public static Builder builder() {
        return new EventGenerator().new Builder();
    }

    public void start() {
        Runnable genTask;
        if (!isRandomDelay) {
            genTask = () -> {
                logger.info("Generating audio event..");
                apiService.sendAudioEvent(new RandomAudioEvent());
            };
            scheduledExecutorService.scheduleWithFixedDelay(genTask, 0, delay.get(), TimeUnit.SECONDS);
        } else {
            delay = new AtomicInteger(
                    ThreadLocalRandom.current().nextInt(randDelayLeftBound, randDelayRightBound));

            logger.info("Next event will be generated in " + delay.get() + "s..");

            genTask = () -> {
                logger.info("Generating audio event..");
                apiService.sendAudioEvent(new RandomAudioEvent());
                this.start();
            };
            scheduledExecutorService.schedule(genTask, delay.get(), TimeUnit.SECONDS);
        }
    }

    /**
     * Builder of EventGenerator.
     */
    public class Builder {
        public Builder() {
        }

        public Builder withDelay(int seconds) {
            EventGenerator.this.isRandomDelay = false;
            EventGenerator.this.delay = new AtomicInteger(seconds);
            return this;
        }

        public Builder withRandomDelay() {
            EventGenerator.this.isRandomDelay = true;
            return this;
        }

        public Builder withRandomDelay(int min, int max) {
            EventGenerator.this.randDelayLeftBound = min;
            EventGenerator.this.randDelayRightBound = max;
            EventGenerator.this.isRandomDelay = true;
            return this;
        }

        public EventGenerator build() {
            return EventGenerator.this;
        }
    }
}
