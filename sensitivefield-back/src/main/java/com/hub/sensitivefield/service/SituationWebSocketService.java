package com.hub.sensitivefield.service;


import com.hub.sensitivefield.dto.AudioEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SituationWebSocketService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final AudioSensorService audioSensorService;

    @Autowired
    public SituationWebSocketService(SimpMessagingTemplate simpMessagingTemplate
                                    , AudioSensorService audioSensorService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.audioSensorService = audioSensorService;
    }

    public void sendNewEvent(AudioEventDTO audioEventDTO) {
        simpMessagingTemplate.convertAndSend("/topic/new-event", audioEventDTO);
    }

    public void getLastEvents(String session_id, int count) {

        simpMessagingTemplate.convertAndSend("/topic/get-last-events"
                + session_id, audioSensorService.getSomeAudioSensors(count));
    }

    public void getSensorState(String session_id, int id) {

        simpMessagingTemplate.convertAndSend("/topic/get-sensor-state"
                + session_id, audioSensorService.getAudioSensorById(id));
    }

    public void getSensors(String session_id) {
        simpMessagingTemplate.convertAndSend("/topic/get-sensors"
                + session_id, audioSensorService.getAllAudioSensors());
    }
}