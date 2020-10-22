package com.hub.sensitivefield.DTO;

import com.hub.sensitivefield.model.KindEvent;

import java.time.LocalDateTime;

public class AudioEventDTO {

    private int id;

    private int sensor_id;

    private double latitude;

    private double longitude;

    private LocalDateTime dateServer;

    private LocalDateTime dateReal;

    private String typeSource1;

    private double persistenceSource1;

    private String typeSource2;

    private double persistenceSource2;

    private String typeSource3;

    private double persistenceSource3;

    private boolean isDeleted;

    private String kindEvent;

    private double longitudeSensor;

    private double latitudeSensor;

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

    public double getLongitudeSensor() {
        return longitudeSensor;
    }

    public void setLongitudeSensor(double longitudeSensor) {
        this.longitudeSensor = longitudeSensor;
    }

    public double getLatitudeSensor() {
        return latitudeSensor;
    }

    public void setLatitudeSensor(double latitudeSensor) {
        this.latitudeSensor = latitudeSensor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDateServer() {
        return dateServer.toString().replace('T', ' ').split("\\.")[0];
    }

    public void setDateServer(LocalDateTime dateServer) {
        this.dateServer = dateServer;
    }


    public String getDateReal() {
        return dateReal.toString().replace('T', ' ').split("\\.")[0];
    }

    public void setDateReal(LocalDateTime dateReal) {
        this.dateReal = dateReal;
    }

    public String getTypeSource1() {
        return typeSource1;
    }

    public void setTypeSource1(String typeSource1) {
        this.typeSource1 = typeSource1;
    }

    public double getPersistenceSource1() {
        return persistenceSource1;
    }

    public void setPersistenceSource1(double persistenceSource1) {
        this.persistenceSource1 = persistenceSource1;
    }

    public String getTypeSource2() {
        return typeSource2;
    }

    public void setTypeSource2(String typeSource2) {
        this.typeSource2 = typeSource2;
    }

    public double getPersistenceSource2() {
        return persistenceSource2;
    }

    public void setPersistenceSource2(double persistenceSource2) {
        this.persistenceSource2 = persistenceSource2;
    }

    public String getTypeSource3() {
        return typeSource3;
    }

    public void setTypeSource3(String typeSource3) {
        this.typeSource3 = typeSource3;
    }

    public double getPersistenceSource3() {
        return persistenceSource3;
    }

    public void setPersistenceSource3(double persistenceSource3) {
        this.persistenceSource3 = persistenceSource3;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getKindEvent() {
        return kindEvent;
    }

    public void setKindEvent(String kindEvent) {
        this.kindEvent = kindEvent;
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
