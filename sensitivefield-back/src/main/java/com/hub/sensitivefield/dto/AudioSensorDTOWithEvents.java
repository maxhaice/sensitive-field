package com.hub.sensitivefield.dto;

import com.hub.sensitivefield.intermediate.AudioEventWithoutSensor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class AudioSensorDTOWithEvents {

    @Setter
    private int id;

    @Setter
    private double latitude;

    @Setter
    private double longitude;

    @Setter
    private String name;

    @Setter
    @Getter
    private List<AudioEventWithoutSensor> audioEventDTOList;

    public AudioSensorDTOWithEvents(int id, String name,
                                    double latitude, double longitude,
                                    List<AudioEventWithoutSensor> audioEventDTOList) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.audioEventDTOList = audioEventDTOList;
    }
}