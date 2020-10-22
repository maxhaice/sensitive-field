package com.hub.sensitivefield.ValueObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Latitude {

    private static Logger logger = LoggerFactory.getLogger(Latitude.class);

    public Latitude(double value) {
        if (!Latitude.isValid(value)) {
            logger.error("Latitude invalid");
            throw new IllegalArgumentException("latitude");
        }

        this.value = value;
    }

    private double value;

    public double getValue() {
        return value;
    }

    protected static boolean isValid(double value) {
        return String.valueOf(value).split("\\.")[1].length() <= 10;
    }
}
