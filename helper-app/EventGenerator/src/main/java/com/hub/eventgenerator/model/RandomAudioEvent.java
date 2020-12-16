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

        AudioSensor sensor;
        Source source;
        if (isExistingSensor) {
            sensor = randomizer.getExistingSensor(true);
        } else {
            sensor = randomizer.getRandomSensor();
        }
        source = randomizer.getRandomSource(sensor.getCoordinates());

        super.setSensorId(sensor.getId());
        super.setSensorCoordinates(sensor.getCoordinates());
        super.setSource(source);
    }
}
