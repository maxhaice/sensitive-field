package com.hub.sensitivefield.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event_kind")
@Getter
@Setter
public class KindEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "priority")
    private String priority;

    @Column(name = "name")
    private String name;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_event_id")
    private TypeEvent typeEvent;

    @JsonBackReference
    @OneToMany(mappedBy = "kindEvent")
    private List<AudioEvent> audioEventList;

    public KindEvent(String priority, String name, TypeEvent typeEvent) {
        this.priority = priority;
        this.name = name;
        this.typeEvent = typeEvent;
        this.audioEventList = new ArrayList<>();
    }

    public String getTypeEventName() {
        return typeEvent.getName();
    }

    public KindEvent() {
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
}