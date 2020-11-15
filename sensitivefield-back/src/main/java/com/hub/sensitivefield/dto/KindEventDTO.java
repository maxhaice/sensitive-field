package com.hub.sensitivefield.dto;

import com.hub.sensitivefield.model.TypeEvent;
import lombok.Setter;

public class KindEventDTO {

    @Setter
    private int id;

    @Setter
    private String name;

    @Setter
    private String typeEvent;

    @Setter
    private String priorityEvent;

    public KindEventDTO(int id, String name, TypeEvent typeEvent, String priorityEvent) {
        this.id = id;
        this.name = name;
        this.typeEvent = typeEvent.getName();
        this.priorityEvent = priorityEvent;
    }
}