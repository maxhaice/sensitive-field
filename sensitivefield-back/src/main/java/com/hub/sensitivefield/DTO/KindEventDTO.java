package com.hub.sensitivefield.DTO;

import com.hub.sensitivefield.model.TypeEvent;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
