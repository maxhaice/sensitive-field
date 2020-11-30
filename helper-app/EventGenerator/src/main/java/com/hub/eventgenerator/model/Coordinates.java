package com.hub.eventgenerator.model;

/**
 * Represents geographical point coordinates: latitude and longitude.
 */
public class Coordinates {
    private final double lat;
    private final double lon;

    public Coordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
