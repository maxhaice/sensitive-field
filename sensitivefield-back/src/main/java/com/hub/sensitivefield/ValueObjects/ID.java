package com.hub.sensitivefield.ValueObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ID {
    private static final Logger logger = LoggerFactory.getLogger(ID.class);
    private final int id;

    public ID(int id) {
        if (!ID.isValid(id)) {
            logger.error("id invalid");
            throw new IllegalArgumentException("id");
        }

        this.id = id;
    }


    public int getValue() {
        return this.id;
    }

    protected static boolean isValid(int id) {
        return id > 0;
    }
}
