package com.hub.sensitivefield.dto.newDTO;


import com.hub.sensitivefield.model.PriorityEvent;
import lombok.Getter;

@Getter
public class NewKindEventDTO {

    private final String name;
    private final String typeEvent;
    private final String priorityEvent;

    public NewKindEventDTO(String name, String typeEvent, PriorityEvent priorityEvent) {
        this.name = name;
        this.typeEvent = typeEvent;
        this.priorityEvent = priorityEvent.name();
    }
}