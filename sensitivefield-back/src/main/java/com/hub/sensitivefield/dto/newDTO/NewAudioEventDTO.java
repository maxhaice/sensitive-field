package com.hub.sensitivefield.dto.newDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class NewAudioEventDTO {

    @JsonProperty("sensor_id")
    private int sensorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("date_real")
    private LocalDateTime dateReal;

    @JsonProperty("sensor_coordinates")
    JsonNode sensorCoordinates;

    @JsonProperty("source")
    JsonNode source;

    public int getSensorId() {
        return sensorId;
    }

    public LocalDateTime getDateReal() {
        return dateReal;
    }

    public JsonNode getSensorCoordinates() {
        return sensorCoordinates;
    }

    public JsonNode getCoordinate() {
        return source.get("coordinates");
    }

    public JsonNode getTypes() {
        return source.get("types");
    }

    @Override
    public String toString() {
        return "newAudioEventDTO{" +
                "sensorId=" + sensorId +
                ", dateReal=" + dateReal +
                ", sensorCoordinates=" + sensorCoordinates +
                ", source=" + source +
                '}';
    }
}