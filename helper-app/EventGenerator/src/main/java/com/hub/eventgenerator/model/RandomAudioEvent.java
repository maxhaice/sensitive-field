package com.hub.eventgenerator.model;

import com.hub.eventgenerator.service.DataRandomizer;

/**
 * RandomAudioEvent class represents AudioEvent class,
 * but fields initialized with random values.
 */
public class RandomAudioEvent extends AudioEvent {
    public RandomAudioEvent() {
        super();
        DataRandomizer randomizer = new DataRandomizer();
        Coordinates sensorCoordinates = randomizer.getRandomSensorCoordinates();
        super.setSensorId(randomizer.getRandomSensorId());
        super.setSensorCoordinates(sensorCoordinates);
        super.setSource(randomizer.getRandomSource(sensorCoordinates));
    }
}
