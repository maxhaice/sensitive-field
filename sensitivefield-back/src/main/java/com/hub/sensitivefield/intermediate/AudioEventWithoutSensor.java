package com.hub.sensitivefield.intermediate;

import com.hub.sensitivefield.model.KindEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AudioEventWithoutSensor {

        private final int id;

        private final LocalDateTime dateServer;

        private final LocalDateTime dateReal;

        private final String typeSource1;

        private final double persistenceSource1;

        private final String typeSource2;

        private final double persistenceSource2;

        private final String typeSource3;

        private final double persistenceSource3;

        private final boolean isDeleted;

        private final String kindEvent;


    public AudioEventWithoutSensor(int id, LocalDateTime dateServer, LocalDateTime dateReal,
                                   String typeSource1, double persistenceSource1, String typeSource2, double persistenceSource2,
                                   String typeSource3, double persistenceSource3, boolean isDeleted, KindEvent kindEvent) {
        this.id = id;
        this.dateServer = dateServer;
        this.dateReal = dateReal;
        this.typeSource1 = typeSource1;
        this.persistenceSource1 = persistenceSource1;
        this.typeSource2 = typeSource2;
        this.persistenceSource2 = persistenceSource2;
        this.typeSource3 = typeSource3;
        this.persistenceSource3 = persistenceSource3;
        this.isDeleted = isDeleted;
        this.kindEvent = kindEvent.getName();
    }
}