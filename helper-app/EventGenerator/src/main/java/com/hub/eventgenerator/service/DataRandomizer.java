package com.hub.eventgenerator.service;

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
    private final ApiService apiService;
    private List<String> kindsNames;
    private List<Integer> availableSensors;

    public DataRandomizer() {
        apiService = new ApiService();
        kindsNames = new ArrayList<>();
        availableSensors = new ArrayList<>();
    }

    public int getRandomSensorId() {
        availableSensors = apiService.getAvailableAudioSensors();
        return availableSensors.get(ThreadLocalRandom.current().nextInt() % availableSensors.size());
    }

    public Coordinates getRandomSensorCoordinates() {
        double latitude = BigDecimal.valueOf(
                ThreadLocalRandom.current().nextDouble(22, 40))
                .setScale(6, RoundingMode.HALF_EVEN)
                .doubleValue();

        double longitude = BigDecimal.valueOf(
                ThreadLocalRandom.current().nextDouble(45, 52))
                .setScale(6, RoundingMode.HALF_EVEN)
                .doubleValue();

        return new Coordinates(latitude, longitude);
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

    private Coordinates getRandomSourceCoordinates(Coordinates sensorCoordinates) {
        double latitude = BigDecimal.valueOf(
                sensorCoordinates.getLat() + ThreadLocalRandom.current().nextDouble(-0.01, 0.01))
                .setScale(6, RoundingMode.HALF_EVEN)
                .doubleValue();

        double longitude = BigDecimal.valueOf(
                sensorCoordinates.getLon() + ThreadLocalRandom.current().nextDouble(-0.01, 0.01))
                .setScale(6, RoundingMode.HALF_EVEN)
                .doubleValue();

        return new Coordinates(latitude, longitude);
    }
}
