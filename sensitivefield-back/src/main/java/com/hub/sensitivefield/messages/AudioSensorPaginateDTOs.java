package com.hub.sensitivefield.messages;

import com.hub.sensitivefield.dto.AudioSensorDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AudioSensorPaginateDTOs {

    private final List<AudioSensorDTO> audioSensors;

    private final int totalPages;

    public AudioSensorPaginateDTOs(List<AudioSensorDTO> audioSensors, int totalPages) {
        this.audioSensors = audioSensors;
        this.totalPages = totalPages;
    }
}
