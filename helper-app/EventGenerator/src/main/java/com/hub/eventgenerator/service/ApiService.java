package com.hub.eventgenerator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hub.eventgenerator.model.AudioEvent;
import com.hub.eventgenerator.model.AudioSensor;
import com.hub.eventgenerator.model.Coordinates;
import com.hub.eventgenerator.utility.AudioEventSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements methods for interactions with SensitiveField backend via REST API.
 */
public class ApiService {
    public static final Logger logger = LogManager.getLogger(EventGenerator.class);
    private static final String API_BASE_URL = "http://localhost:8080/api";

    private final HttpService httpService;
    private final AudioEventSerializer audioEventSerializer;
    private final ObjectMapper objectMapper;

    public ApiService() {
        this.httpService = new HttpService();
        this.audioEventSerializer = new AudioEventSerializer();
        this.objectMapper = new ObjectMapper();
    }

    public void sendAudioEvent(AudioEvent audioEvent) {
        String audioEventJson = null;
        try {
            audioEventJson = audioEventSerializer.serialize(audioEvent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.info("Generated event:\n" + audioEventJson);
        HttpResponse<?> response = httpService.post(API_BASE_URL + "/audio-events/", audioEventJson);
        logger.debug("Response HTTP status code - " + response.statusCode() + ".");
    }

    public List<String> getAudioEventKinds() {
        List<String> result = new ArrayList<>();

        String json = httpService.get("http://localhost:8080/api/kinds-events/").body().toString();
        try {
            JsonNode node = objectMapper.readTree(json);
            for (JsonNode subNode : node) {
                result.add(subNode.get("name").toString().replace("\"", ""));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.debug("List of available event kinds: [" + String.join(", ", result) + "].");
        return result;
    }

    public List<AudioSensor> getAvailableAudioSensors() {
        List<AudioSensor> sensors = new ArrayList<>();

        String json = httpService.get(API_BASE_URL + "/audio-sensors/").body().toString();
        try {
            JsonNode node = objectMapper.readTree(json);
            for (JsonNode subNode : node) {
                int id = subNode.get("id").asInt();
                double lat = subNode.get("latitude").asDouble();
                double lon = subNode.get("longitude").asDouble();

                sensors.add(new AudioSensor(id, new Coordinates(lat, lon)));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.debug("List of available sensors: [" + sensors.stream()
                .map((s) -> String.valueOf(s.getId()))
                .collect(Collectors.joining(", ")) + "].");
        return sensors;
    }

    public boolean isBackendRun() {
        return httpService.checkConnection("localhost", 8080);
    }
}

