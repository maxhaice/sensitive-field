package com.hub.sensitivefield.dto;

import com.hub.sensitivefield.model.KindEvent;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AudioEventDTO {

    private final int id;

    private final int sensor_id;

    private final double latitude;

    private final double longitude;

    private final LocalDateTime dateServer;

    private final LocalDateTime dateReal;

    private final String typeSource1;

    private final double persistenceSource1;

    private final String typeSource2;

    private final double persistenceSource2;

    private final String typeSource3;

    private final double persistenceSource3;

    private final boolean isDeleted;

    private final String kindEvent;

    private final double longitudeSensor;

    private final double latitudeSensor;

    public AudioEventDTO(int id, int sensor_id, double latitudeSensor, double longitudeSensor, double latitude, double longitude, LocalDateTime dateServer, LocalDateTime dateReal,
                         String typeSource1, double persistenceSource1, String typeSource2, double persistenceSource2,
                         String typeSource3, double persistenceSource3, boolean isDeleted, KindEvent kindEvent) {
        this.id = id;
        this.sensor_id = sensor_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateServer = dateServer;
        this.dateReal = dateReal;
        this.typeSource1 = typeSource1;
        this.persistenceSource1 = persistenceSource1;
        this.typeSource2 = typeSource2;
        this.persistenceSource2 = persistenceSource2;
        this.typeSource3 = typeSource3;
        this.persistenceSource3 = persistenceSource3;
        this.isDeleted = isDeleted;
        this.kindEvent = kindEvent.getName();
        this.latitudeSensor = latitudeSensor;
        this.longitudeSensor = longitudeSensor;
    }

    @Override
    public String toString() {
        return "AudioEventDTO{" +
                "id=" + id +
                ", sensor_id=" + sensor_id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", dateServer=" + dateServer +
                ", dateReal=" + dateReal +
                ", typeSource1='" + typeSource1 + '\'' +
                ", persistenceSource1=" + persistenceSource1 +
                ", typeSource2='" + typeSource2 + '\'' +
                ", persistenceSource2=" + persistenceSource2 +
                ", typeSource3='" + typeSource3 + '\'' +
                ", persistenceSource3=" + persistenceSource3 +
                '}';
    }
}