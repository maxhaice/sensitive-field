package com.hub.sensitivefield.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeEventDTO {

    private final int id;
    private final String name;

    public TypeEventDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}