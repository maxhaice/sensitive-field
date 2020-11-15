package com.hub.sensitivefield.controller;

import com.hub.sensitivefield.dto.AudioSensorDTO;
import com.hub.sensitivefield.dto.newDTO.NewAudioSensorDTO;
import com.hub.sensitivefield.model.AudioSensor;
import com.hub.sensitivefield.service.AudioSensorService;
import com.hub.sensitivefield.service.SituationWebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/audio-sensors")
public class AudioSensorController {

    private static final Logger logger = LoggerFactory.getLogger(AudioSensorController.class);

    private final AudioSensorService audioSensorService;

    private final SituationWebSocketService situationWebSocketService;

    @Autowired
    public AudioSensorController(AudioSensorService audioSensorService, SituationWebSocketService situationWebSocketService) {
        this.audioSensorService = audioSensorService;
        this.situationWebSocketService = situationWebSocketService;
    }

    @GetMapping("/")
    private ResponseEntity<List<AudioSensorDTO>> getAllAudioSensors() {
        logger.info("Audio Sensor was SEND");
        return ResponseEntity.ok(audioSensorService.getAllAudioSensors());
    }

    @GetMapping("/id/{id}")
    private ResponseEntity<AudioSensorDTO> getAudioSensorById(@PathVariable int id) {
        Optional<AudioSensor> audioSensor = audioSensorService.getAudioSensorById(id);
        if (audioSensor.isEmpty()) {
            logger.info("Audio with id = " + id + " WASN'T FOUND");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Audio with id = " + id + " was SEND");
            return ResponseEntity.ok(audioSensorService.convertToDTO(audioSensor.get()));
        }
    }

    @GetMapping("/name/{name}")
    private ResponseEntity<AudioSensorDTO> getAudioSensorByName(@PathVariable String name) {
        Optional<AudioSensor> audioSensor = audioSensorService.getAudioSensorByName(name);
        if (audioSensor.isEmpty()) {
            logger.info("Audio with name = " + name + " WASN'T FOUND");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Audio with name = " + name + " was SEND");
            return ResponseEntity.ok(audioSensorService.convertToDTO(audioSensor.get()));
        }
    }

    @PostMapping("/")
    private ResponseEntity<Void> addAudioSensor(@RequestBody NewAudioSensorDTO newAudioSensorDTO) {
        try{
            audioSensorService.saveAudioSensor(newAudioSensorDTO);
            logger.info("AudioSensor WAS EDIT");
        } catch (Exception e) {
            logger.info("Something data from AudioSensor EDIT WAS WRONG");
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/coordinates/{latitude}/{longitude}")
    private ResponseEntity<Void> changeAudioSensorCoordinates(@PathVariable int id, @PathVariable double latitude, @PathVariable double longitude) {
        if (audioSensorService.changeSensorCoordinates(id, latitude, longitude)) {
            logger.info("Audio WAS EDIT");
            return ResponseEntity.ok().build();
        } else {
            logger.info("Audio Sensor with id = " + id + " WASN'T FOUND");
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}/name/{name}")
    private ResponseEntity<?> changeAudioSensorName(@PathVariable String name, @PathVariable int id){
        if (audioSensorService.changeAudioSensorName(id,name)) {
            logger.info("Audio Sensor name with id = " + id + " WAS CHANGED");
            return ResponseEntity.ok().build();
        }
        else {
            logger.info("Audio Sensor with id = " + id + " WASN'T FOUND");
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/api/audio-sensor/{id}")
    private ResponseEntity<Void> removeAudioSensor(@PathVariable int id) {
        if (audioSensorService.removeAudioSensorById(id)) {
            logger.info("Audio Sensor with id = " + id + " was DELETED");
            return ResponseEntity.ok().build();
        } else {
            logger.info("Audio Sensor with id = " + id + " WASN'T FOUND");
            return ResponseEntity.noContent().build();
        }
    }
}