package com.hub.sensitivefield.dto;

import lombok.Setter;

public class AudioSensorDTO {

    @Setter
    private int id;
    @Setter
    private double latitude;
    @Setter
    private double longitude;

    public AudioSensorDTO(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}