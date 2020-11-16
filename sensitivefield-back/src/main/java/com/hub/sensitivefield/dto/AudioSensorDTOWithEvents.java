package com.hub.sensitivefield.dto;

import com.hub.sensitivefield.intermediate.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AudioSensorDTOWithEvents {

    private int id;

    private double latitude;

    private double longitude;

    private String name;

    private LocalDateTime date;

    private List<AudioEventWithoutSensor> audioEventDTOList;

    public AudioSensorDTOWithEvents(int id, String name,
                                    double latitude, double longitude,
                                    List<AudioEventWithoutSensor> audioEventDTOList,
                                    LocalDateTime localDateTime) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.audioEventDTOList = audioEventDTOList;
        this.date = localDateTime;
    }
}