package com.hub.sensitivefield.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AudioSensorDTO {

    private int id;

    private double latitude;

    private double longitude;

    public AudioSensorDTO(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}