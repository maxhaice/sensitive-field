package com.hub.sensitivefield.model;

import com.hub.sensitivefield.ValueObjects.ID;
import com.hub.sensitivefield.ValueObjects.Latitude;
import com.hub.sensitivefield.ValueObjects.Longitude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AudioSensor {

    public AudioSensor(ID id, Latitude latitude, Longitude longitude) {
        this.id = id.getValue();
        this.latitude = latitude.getValue();
        this.longitude = longitude.getValue();
        this.audioEvents = new ArrayList<>();
        this.name = "blank";
    }

    public AudioSensor(ID id, Latitude latitude, Longitude longitude, String name) {
        this.id = id.getValue();
        this.latitude = latitude.getValue();
        this.longitude = longitude.getValue();
        this.audioEvents = new ArrayList<>();
        this.name = name;
    }

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "audioSensor")
    private List<AudioEvent> audioEvents;


    public void addAudioEvent(AudioEvent audioEvent) {
        List<AudioEvent> audioEvents = getAudioEvents();
        audioEvents.add(audioEvent);
        this.setAudioEvents(audioEvents);
    }

}
