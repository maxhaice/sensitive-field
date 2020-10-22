package com.hub.sensitivefield.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AudioEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_audioevent")
    private int id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "audiosensor_id", nullable = false)
    private AudioSensor audioSensor;

    @Column(name = "latitude_audioevent")
    private double latitude;

    @Column(name = "longitude_audioevent")
    private double longitude;

    @Column(name = "dateserver_audioevent")
    private LocalDateTime dateServer;

    @Column(name = "datereal_audioevent")
    private LocalDateTime dateReal;

    @Column(name = "typeSource_audioEvent1")
    private String typeSource1;

    @Column(name = "persistenceSource_audioEvent1")
    private double persistenceSource1;

    @Column(name = "typeSource_audioEvent2")
    private String typeSource2;

    @Column(name = "persistenceSource_audioEvent2")
    private double persistenceSource2;

    @Column(name = "typeSource_audioEvent3")
    private String typeSource3;

    @Column(name = "persistenceSource_audioEvent3")
    private double persistenceSource3;

    @Column(name = "isdeleted_audioevent")
    private boolean isDeleted = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kindevent_id", nullable = false)
    private KindEvent kindEvent;

    public AudioEvent(AudioSensor audioSensor, double latitude, double longitude, LocalDateTime dateServer, LocalDateTime dateReal, String typeSource1,
                      double persistenceSource1, String typeSource2, double persistenceSource2, String typeSource3, double persistenceSource3,
                      KindEvent kindEvent) {
        this.audioSensor = audioSensor;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateServer = dateServer;
        this.dateReal = dateReal;
        this.typeSource1 = typeSource1;
        this.persistenceSource1 = persistenceSource1;
        this.typeSource2 = typeSource2;
        this.persistenceSource2 = persistenceSource2;
        this.typeSource3 = typeSource3;
        this.persistenceSource3 = persistenceSource3;
        this.kindEvent = kindEvent;
        this.isDeleted = false;
    }

    @Override
    public String toString() {
        return "AudioEvent{" +
                "id=" + id +
                ", audioSensor=" + audioSensor +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", dateServer=" + dateServer +
                ", dateReal=" + dateReal +
                ", typeSource1='" + typeSource1 + '\'' +
                ", persistenceSource1=" + persistenceSource1 +
                ", typeSource2='" + typeSource2 + '\'' +
                ", persistenceSource2=" + persistenceSource2 +
                ", typeSource3='" + typeSource3 + '\'' +
                ", persistenceSource3=" + persistenceSource3 +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
