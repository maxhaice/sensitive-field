package com.hub.sensitivefield.ValueObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Latitude {

    private static final Logger logger = LoggerFactory.getLogger(Latitude.class);
    private final double value;

    public Latitude(double value) {
        if (!Latitude.isValid(value)) {
            logger.error("Latitude invalid");
            throw new IllegalArgumentException("latitude");
        }

        this.value = value;
    }

    protected static boolean isValid(double value) {
        return String.valueOf(value).split("\\.")[1].length() <= 10;
    }

    public double getValue() {
        return value;
    }
}
