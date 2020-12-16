package com.hub.eventgenerator.model;

/**
 * Audio sensor model for storing id and coordinates.
 */
public class AudioSensor {
    private int id;
    private Coordinates coordinates;

    public AudioSensor(int id, Coordinates coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

    public AudioSensor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
