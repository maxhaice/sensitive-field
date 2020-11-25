package com.hub.sensitivefield.model;

import com.hub.sensitivefield.valueobjects.ID;
import com.hub.sensitivefield.valueobjects.Latitude;
import com.hub.sensitivefield.valueobjects.Longitude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    @OneToMany(mappedBy = "audioSensor", cascade = CascadeType.ALL)
    private List<AudioEvent> audioEvents;

    public AudioSensor(ID id, Latitude latitude, Longitude longitude) {
        this.id = id.getValue();
        this.latitude = latitude.getValue();
        this.longitude = longitude.getValue();
        this.audioEvents = new ArrayList<>();
        this.name = "blank";
    }
    public void addAudioEvent(AudioEvent audioEvent) {
        List<AudioEvent> audioEvents = getAudioEvents();
        audioEvents.add(audioEvent);
        this.setAudioEvents(audioEvents);
    }
}