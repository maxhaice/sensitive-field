package com.hub.sensitivefield.dto;

import com.hub.sensitivefield.intermediate.AudioEventWithoutSensor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AudioSensorDTOWithEvents {

    private int id;

    private double latitude;

    private double longitude;

    private String name;

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