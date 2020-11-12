package com.hub.sensitivefield.DTO;

import java.util.List;

public class AudioSensorDTOwithEvents {

    private int id;
    private double latitude;
    private double longitude;
    private String name;
    private List<AudioEventDTOwithoutSensor> audioEventDTOList;

    public AudioSensorDTOwithEvents(int id, String name,
                                    double latitude, double longitude,
                                    List<AudioEventDTOwithoutSensor> audioEventDTOList) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.audioEventDTOList = audioEventDTOList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<AudioEventDTOwithoutSensor> getAudioEventDTOList() {
        return audioEventDTOList;
    }

    public void setAudioEventDTOList(List<AudioEventDTOwithoutSensor> audioEventDTOList) {
        this.audioEventDTOList = audioEventDTOList;
    }
}
