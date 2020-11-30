package com.hub.sensitivefield.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_type_id")
    private TypeEvent typeEvent;

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