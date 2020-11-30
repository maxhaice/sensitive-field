package com.hub.sensitivefield.dto;

import com.hub.sensitivefield.model.TypeEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KindEventDTO {

    private int id;

    private String name;

    private String typeEvent;

    private String priorityEvent;

    public KindEventDTO(int id, String name, TypeEvent typeEvent, String priorityEvent) {
        this.id = id;
        this.name = name;
        this.typeEvent = typeEvent.getName();
        this.priorityEvent = priorityEvent;
    }
}