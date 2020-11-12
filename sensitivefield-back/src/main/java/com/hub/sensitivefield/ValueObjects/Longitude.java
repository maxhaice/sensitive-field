package com.hub.sensitivefield.ValueObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Longitude {

    private static final Logger logger = LoggerFactory.getLogger(Longitude.class);
    private double value;

    private Longitude() {
    }

    public Longitude(double value) {
        if (!Longitude.isValid(value)) {
            logger.error("Longitude invalid");
            throw new IllegalArgumentException("longitude");
        }

        this.value = value;
    }

    public double getValue() {
        return value;
    }

    protected static boolean isValid(double value) {
        return String.valueOf(value).split("\\.")[1].length() <= 10;
    }
}
