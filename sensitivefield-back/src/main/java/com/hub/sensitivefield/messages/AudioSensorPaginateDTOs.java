package com.hub.sensitivefield.messages;

import com.hub.sensitivefield.dto.AudioSensorDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class AudioSensorPaginateDTOs {

    private final List<AudioSensorDTO> audioSensors;

    private final int totalPages;

    public AudioSensorPaginateDTOs(List<AudioSensorDTO> audioSensors, int totalPages) {
        this.audioSensors = audioSensors;
        this.totalPages = totalPages;
    }
}
