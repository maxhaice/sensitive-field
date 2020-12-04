package com.hub.eventgenerator.service;

import com.hub.eventgenerator.model.AudioSensor;
import com.hub.eventgenerator.model.Coordinates;
import com.hub.eventgenerator.model.Source;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generate valid random values for AudioEvent objects.
 */
public class DataRandomizer {
    // maximum number of sensors
    public static final int MAX_SENSOR_ID = 100;
    // represents Kyiv bounds
    private static final double MIN_LATITUDE = 50.45;
    private static final double MAX_LATITUDE = 50.53;
    private static final double MIN_LONGITUDE = 30.61;
    private static final double MAX_LONGITUDE = 30.72;
    // coordinates delta
    private static final double SENSOR_COORDS_DIFF = 0.0002;
    private static final double SOURCE_COORDS_DIFF = 0.002;

    private final ApiService apiService;
    private List<String> kindsNames;
    private List<AudioSensor> availableSensors;

    public DataRandomizer() {
        apiService = new ApiService();
        kindsNames = new ArrayList<>();
        availableSensors = new ArrayList<>();
    }

    public AudioSensor getExistingSensor() {
        availableSensors = apiService.getAvailableAudioSensors();
        return availableSensors.get(ThreadLocalRandom.current().nextInt() % availableSensors.size());
    }

    public AudioSensor getExistingSensor(boolean isUpdateCoordinates) {
        availableSensors = apiService.getAvailableAudioSensors();
        AudioSensor sensor = availableSensors.get(ThreadLocalRandom.current().nextInt() % availableSensors.size());
        if (isUpdateCoordinates) {
            sensor.setCoordinates(getRandomSensorCoordinates(sensor.getCoordinates()));
        }
        return sensor;
    }

    public AudioSensor getRandomSensor() {
        int id = ThreadLocalRandom.current().nextInt(MAX_SENSOR_ID);

        double latitude = BigDecimal.valueOf(
                ThreadLocalRandom.current().nextDouble(MIN_LATITUDE, MAX_LATITUDE))
                .setScale(5, RoundingMode.HALF_EVEN)
                .doubleValue();

        double longitude = BigDecimal.valueOf(
                ThreadLocalRandom.current().nextDouble(MIN_LONGITUDE, MAX_LONGITUDE))
                .setScale(5, RoundingMode.HALF_EVEN)
                .doubleValue();

        return new AudioSensor(id, new Coordinates(latitude, longitude));
    }

    public Source getRandomSource(Coordinates sensorCoordinates) {
        kindsNames = apiService.getAudioEventKinds();
        List<Source.PotentialType> potentialSourceTypeList = new ArrayList<>();

        double[] probabilities = new double[3];
        probabilities[0] = ThreadLocalRandom.current().nextDouble(50, 99);
        probabilities[1] = ((100 - probabilities[0]) / 3) * 2;
        probabilities[2] = 100 - probabilities[0] - probabilities[1];

        for (int i = 0; i < 3; i++) {
            String randTypeName = kindsNames.get(ThreadLocalRandom.current().nextInt(kindsNames.size()));
            kindsNames.remove(randTypeName);

            probabilities[i] = BigDecimal.valueOf(probabilities[i])
                    .setScale(3, RoundingMode.HALF_EVEN)
                    .doubleValue();
            potentialSourceTypeList.add(new Source.PotentialType(randTypeName, probabilities[i]));
        }

        return new Source(getRandomSourceCoordinates(sensorCoordinates), potentialSourceTypeList);
    }

    private Coordinates getRandomSensorCoordinates(Coordinates initialCoordinates) {
        double latitude = BigDecimal.valueOf(
                initialCoordinates.getLat() + ThreadLocalRandom.current().nextDouble(-SENSOR_COORDS_DIFF, SENSOR_COORDS_DIFF))
                .setScale(5, RoundingMode.HALF_EVEN)
                .doubleValue();

        double longitude = BigDecimal.valueOf(
                initialCoordinates.getLon() + ThreadLocalRandom.current().nextDouble(-SENSOR_COORDS_DIFF, SENSOR_COORDS_DIFF))
                .setScale(5, RoundingMode.HALF_EVEN)
                .doubleValue();

        return new Coordinates(latitude, longitude);
    }

    private Coordinates getRandomSourceCoordinates(Coordinates sensorCoordinates) {
        double latitude = BigDecimal.valueOf(
                sensorCoordinates.getLat() + ThreadLocalRandom.current().nextDouble(-SOURCE_COORDS_DIFF, SOURCE_COORDS_DIFF))
                .setScale(5, RoundingMode.HALF_EVEN)
                .doubleValue();

        double longitude = BigDecimal.valueOf(
                sensorCoordinates.getLon() + ThreadLocalRandom.current().nextDouble(-SOURCE_COORDS_DIFF, SOURCE_COORDS_DIFF))
                .setScale(5, RoundingMode.HALF_EVEN)
                .doubleValue();
        return new Coordinates(latitude, longitude);
    }
}
