package com.hub.eventgenerator.model;

import java.util.List;

/**
 * Represents audio event source.
 * Contains source coordinates and potential source types.
 */
public class Source {
    private final Coordinates coordinates;
    private final List<PotentialType> types;

    public Source(Coordinates coordinates, List<PotentialType> types) {
        this.coordinates = coordinates;
        this.types = types;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public List<PotentialType> getTypes() {
        return types;
    }

    /**
     * Represent source type with specific probability.
     */
    public static class PotentialType {
        private final String type;
        private final double probability;

        public PotentialType(String type, double probability) {
            this.type = type;
            this.probability = probability;
        }

        public String getType() {
            return type;
        }

        public double getProbability() {
            return probability;
        }
    }
}
