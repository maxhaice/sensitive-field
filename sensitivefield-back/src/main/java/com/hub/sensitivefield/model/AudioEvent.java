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
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "audio_sensor_id", nullable = false)
    private AudioSensor audioSensor;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "date_of_come")
    private LocalDateTime dateServer;

    @Column(name = "date_origin")
    private LocalDateTime dateReal;

    @Column(name = "source_type1")
    private String typeSource1;

    @Column(name = "source_persistence1")
    private double persistenceSource1;

    @Column(name = "source_type2")
    private String typeSource2;

    @Column(name = "source_persistence2")
    private double persistenceSource2;

    @Column(name = "source_type3")
    private String typeSource3;

    @Column(name = "source_persistence3")
    private double persistenceSource3;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_kind_id", nullable = false)
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
