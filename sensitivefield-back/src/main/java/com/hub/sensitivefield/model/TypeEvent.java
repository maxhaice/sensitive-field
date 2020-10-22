package com.hub.sensitivefield.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class TypeEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_typeevent")
    private int id;

    @Column(name = "name_typeevent")
    private String name;

    @OneToMany(mappedBy = "typeEvent")
    private List<KindEvent> kindEvent;
}
