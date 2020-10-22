package com.hub.sensitivefield.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "login_user"
        })
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(name = "name_user")
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(name = "login_user")
    private String login;

    @NotBlank
    @Size(min = 6, max = 100)
    @Column(name = "password_user")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}