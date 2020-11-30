package com.hub.sensitivefield.messages;

import com.hub.sensitivefield.dto.AudioEventDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AudioEventPaginateDTOs {

    private final List<AudioEventDTO> audioEventDTOS;

    private final int totalPages;

    public AudioEventPaginateDTOs(List<AudioEventDTO > audioEventDTOS, int totalPages) {
        this.audioEventDTOS = audioEventDTOS;
        this.totalPages = totalPages;
    }
}
