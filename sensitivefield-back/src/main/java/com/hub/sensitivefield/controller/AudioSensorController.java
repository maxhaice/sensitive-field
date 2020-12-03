package com.hub.sensitivefield.controller;

import com.hub.sensitivefield.dto.AudioSensorDTO;
import com.hub.sensitivefield.dto.newDTO.NewAudioSensorDTO;
import com.hub.sensitivefield.messages.AudioSensorPaginateDTOs;
import com.hub.sensitivefield.model.AudioSensor;
import com.hub.sensitivefield.repository.AudioSensorRepository;
import com.hub.sensitivefield.service.AudioSensorService;
import com.hub.sensitivefield.service.SituationWebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/audio-sensors")
public class AudioSensorController {

    private static final Logger logger = LoggerFactory.getLogger(AudioSensorController.class);

    private final AudioSensorService audioSensorService;

    private final AudioSensorRepository audioSensorRepository;

    private final SituationWebSocketService situationWebSocketService;

    @Autowired
    public AudioSensorController(AudioSensorService audioSensorService,
                                 SituationWebSocketService situationWebSocketService,
                                 AudioSensorRepository audioSensorRepository) {
        this.audioSensorService = audioSensorService;
        this.audioSensorRepository = audioSensorRepository;
        this.situationWebSocketService = situationWebSocketService;
    }

    @GetMapping("")
    private ResponseEntity<?> getFilteredAudioSensors(@RequestParam(required = false) Integer page,
                                                                             @RequestParam(required = false) Integer pageSize,
                                                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                                                @RequestParam(required = false)
                                                                                        LocalDateTime dateAfter,
                                                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                                                @RequestParam(required = false)
                                                                                        LocalDateTime dateBefore,
                                                                             @RequestParam(required = false) String sortBy,
                                                                             @RequestParam(required = false) boolean isDescending,
                                                                             @RequestParam(required = false) String name
    ) {

        if(page==null && pageSize==null){
            return ResponseEntity.ok(audioSensorService.getAllAudioSensorEntity());
        }

        Page<AudioSensor> audioSensors = audioSensorService
                .getFilteredSortedPageableAudioSensors(dateAfter, dateBefore
                                        ,name, sortBy, isDescending, page, pageSize);

//        System.out.println("Total Elements = " + audioSensors.getContent().get(0).getLongitude());
        int totalPages = audioSensors.getTotalPages();

        List<AudioSensorDTO> audioSensorDTOS = audioSensors.stream()
                .map(audioSensorService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new AudioSensorPaginateDTOs(audioSensorDTOS, totalPages));
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

    @PostMapping("")
    private ResponseEntity<Void> addAudioSensor(@RequestBody NewAudioSensorDTO newAudioSensorDTO) {
        try{
            audioSensorService.saveAudioSensor(newAudioSensorDTO);
            logger.info("AudioSensor WAS SAVED");
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