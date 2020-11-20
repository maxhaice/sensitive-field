package com.hub.eventgenerator.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Audio event model that used in SensitiveField project.
 */
public class AudioEvent {
    private int sensorId;
    private final String dateReal;
    private Coordinates sensorCoordinates;
    private Source source;

    protected AudioEvent() {
        this.dateReal = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public AudioEvent(int sensorId, Coordinates sensorCoordinates) {
        this.sensorId = sensorId;
        this.dateReal = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.sensorCoordinates = sensorCoordinates;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getDateReal() {
        return dateReal;
    }

    public Coordinates getSensorCoordinates() {
        return sensorCoordinates;
    }

    public void setSensorCoordinates(Coordinates sensorCoordinates) {
        this.sensorCoordinates = sensorCoordinates;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
