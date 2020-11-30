package com.hub.sensitivefield.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AudioSensorDTO {

    private int id;

    private double latitude;

    private double longitude;

    private LocalDateTime date;

    public AudioSensorDTO(int id, double latitude, double longitude, LocalDateTime localDateTime) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = localDateTime;
    }
}