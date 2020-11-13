package com.hub.sensitivefield.DTO.newDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;

public class newAudioEventDTO {
    JsonNode sensor_coordinates;
    JsonNode source;
    private int sensor_id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date_real;

    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }

    public LocalDateTime getDate_real() {
        return date_real;
    }

    public void setDate_real(LocalDateTime date_real) {
        this.date_real = date_real;
    }

    public JsonNode getSensor_coordinates() {
        return sensor_coordinates;
    }

    public void setSensor_coordinates(JsonNode sensor_coordinates) {
        this.sensor_coordinates = sensor_coordinates;
    }

    public JsonNode getCoordinate() {
        return source.get("coordinates");
    }


    public JsonNode getTypes() {
        return source.get("types");
    }


    public JsonNode getSource() {
        return source;
    }

    public void setSource(JsonNode source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "newAudioEventDTO{" +
                "sensor_id=" + sensor_id +
                ", date_real=" + date_real +
                ", sensor_coordinates=" + sensor_coordinates +
                ", source=" + source +
                '}';
    }
}
