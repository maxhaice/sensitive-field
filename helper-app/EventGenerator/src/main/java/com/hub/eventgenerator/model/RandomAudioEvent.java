package com.hub.eventgenerator.model;

import com.hub.eventgenerator.service.DataRandomizer;

/**
 * RandomAudioEvent class represents AudioEvent class,
 * but fields initialized with random values.
 */
public class RandomAudioEvent extends AudioEvent {
    public RandomAudioEvent(boolean isExistingSensor) {
        super();
        DataRandomizer randomizer = new DataRandomizer();
        Coordinates sensorCoordinates = randomizer.getRandomSensorCoordinates();
        if (isExistingSensor) {
            super.setSensorId(randomizer.getRandomExistingSensorId());
        } else {
            super.setSensorId(randomizer.getRandomSensorId(100));
        }
        super.setSensorCoordinates(sensorCoordinates);
        super.setSource(randomizer.getRandomSource(sensorCoordinates));
    }
}
