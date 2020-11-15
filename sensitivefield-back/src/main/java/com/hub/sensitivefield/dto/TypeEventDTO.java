package com.hub.sensitivefield.dto;

import lombok.Getter;

@Getter
public class TypeEventDTO {

    private final int id;
    private final String name;

    public TypeEventDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}