package com.hub.sensitivefield.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hub.sensitivefield.valueobjects.ID;
import com.hub.sensitivefield.valueobjects.Latitude;
import com.hub.sensitivefield.valueobjects.Longitude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AudioSensor {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "name")
    private String name;

    @Column(name = "time_stamp", length = 0)
    private LocalDateTime timeStamp;

    @JsonBackReference
    @OneToMany(mappedBy = "audioSensor", cascade = CascadeType.ALL)
    private List<AudioEvent> audioEvents;

    public AudioSensor(ID id, Latitude latitude, Longitude longitude, LocalDateTime timeStamp) {
        this.id = id.getValue();
        this.latitude = latitude.getValue();
        this.longitude = longitude.getValue();
        this.audioEvents = new ArrayList<>();
        this.name = "blank";
        this.timeStamp = timeStamp;
    }
    public void addAudioEvent(AudioEvent audioEvent) {
        List<AudioEvent> audioEvents = getAudioEvents();
        audioEvents.add(audioEvent);
        this.setAudioEvents(audioEvents);
    }
}