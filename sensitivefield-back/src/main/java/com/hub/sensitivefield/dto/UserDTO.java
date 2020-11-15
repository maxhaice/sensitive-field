package com.hub.sensitivefield.dto;

import lombok.Getter;

@Getter
public class UserDTO {
    private final String name;
    private final String login;
    private final String password;

    public UserDTO(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}