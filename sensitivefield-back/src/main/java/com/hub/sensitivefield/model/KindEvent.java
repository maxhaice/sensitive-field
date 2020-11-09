package com.hub.sensitivefield.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class KindEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kindevent")
    private int id;

    @Column(name = "priority_kindevent")
    private String priority;

    @Column(name = "name_kindevent")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "typevent_id")
    private TypeEvent typeEvent;

    @OneToMany(mappedBy = "kindEvent")
    private List<AudioEvent> audioEventList;

    public KindEvent(String priority, String name, TypeEvent typeEvent) {
        this.priority = priority;
        this.name = name;
        this.typeEvent = typeEvent;
        this.audioEventList = new ArrayList<>();
    }

    public KindEvent(){
        this.audioEventList = new ArrayList<>();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(PriorityEvent priority) {
        this.priority = priority.name();
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public List<AudioEvent> getAudioEventList() {
        return audioEventList;
    }

    public void setAudioEventList(List<AudioEvent> audioEventList) {
        this.audioEventList = audioEventList;
    }

    public TypeEvent getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(TypeEvent typeEvent) {
        this.typeEvent = typeEvent;
    }
}
