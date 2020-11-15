package com.hub.sensitivefield.messages;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpForm {

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

//    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}