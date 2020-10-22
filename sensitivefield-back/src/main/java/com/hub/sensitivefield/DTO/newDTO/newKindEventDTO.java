package com.hub.sensitivefield.DTO.newDTO;


import com.hub.sensitivefield.model.PriorityEvent;

public class newKindEventDTO {

    private String name;
    private String typeEvent;
    private String priorityEvent;

    public newKindEventDTO(String name, String typeEvent, PriorityEvent priorityEvent) {
        this.name = name;
        this.typeEvent = typeEvent;
        this.priorityEvent = priorityEvent.name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }

    public String getPriorityEvent() {
        return priorityEvent;
    }

    public void setPriorityEvent(String priorityEvent) {
        this.priorityEvent = priorityEvent;
    }
}
