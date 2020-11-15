package com.hub.sensitivefield.controller;

import com.hub.sensitivefield.dto.AudioEventDTO;
import com.hub.sensitivefield.intermediate.AudioEventWithoutSensor;
import com.hub.sensitivefield.intermediate.AudioEventWithoutSensor;
import com.hub.sensitivefield.dto.AudioSensorDTOWithEvents;
import com.hub.sensitivefield.model.AudioEvent;
import com.hub.sensitivefield.service.AudioEventService;
import com.hub.sensitivefield.service.AudioSensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "http://localhost:4200")
public class HistoryController {

    private static final Logger logger = LoggerFactory.getLogger(AudioSensorController.class);

    private final AudioEventService audioEventService;

    private final AudioSensorService audioSensorService;

    @Autowired
    public HistoryController(AudioEventService audioEventService, AudioSensorService audioSensorService) {
        this.audioEventService = audioEventService;
        this.audioSensorService = audioSensorService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<AudioEventDTO> getAudioEventById(@PathVariable int id) {
        Optional<AudioEvent> audioEvent = audioEventService.getAudioEventById(id);
        if (audioEvent.isEmpty()) {
            logger.info("History event with this id = " + id + " WASN'T FOUND");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("History with id = " + id + " WAS SEND");
            return ResponseEntity.ok(
                    AudioEventService.convertToDTO(audioEvent.get())
            );
        }
    }

    @GetMapping("/")
    private ResponseEntity<List<AudioSensorDTOWithEvents>> getEventByDate(@RequestParam(name = "date1") String date1Text,
                                                                          @RequestParam(name = "date2") String date2Text,
                                                                          @RequestParam List<String> nameKindEvent) {
        LocalDateTime localDate1 = LocalDateTime.parse(date1Text,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime localDate2 = LocalDateTime.parse(date2Text,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        List<AudioSensorDTOWithEvents> audioSensorsDTOwithEvents = audioSensorService.getAllAudioSensorEntity()
                .stream()
                .map(audioSensorService::convertToDTOwithEvents)
                .collect(Collectors.toList());

        List<AudioSensorDTOWithEvents> readyToSendDTOs = new ArrayList<>();
        for (AudioSensorDTOWithEvents audioSensorDTOwithEvents : audioSensorsDTOwithEvents) {
            List<AudioEventWithoutSensor> audioEventDTOS = audioSensorDTOwithEvents.getAudioEventDTOList();
            List<AudioEventWithoutSensor> readyToAddToSensor = new ArrayList<>();

            for (AudioEventWithoutSensor audioEventDTO : audioEventDTOS) {
                for (String s : nameKindEvent) {
                    if (audioEventDTO.getKindEvent().equals(s)) {
                        readyToAddToSensor.add(audioEventDTO);
                        break; // when we found kind event in event
                    }
                }
            }

            if ((long) readyToAddToSensor.size() !=0) {
                audioSensorDTOwithEvents.setAudioEventDTOList(readyToAddToSensor);
                readyToSendDTOs.add(audioSensorDTOwithEvents);
            }
        }

        if(localDate1.isAfter(localDate2)) {//FIRST DATE IS AFTER DATE2
            return ResponseEntity.badRequest().build();
        }

        logger.info("History by this date1 = " + date1Text + " date2 = " + date2Text + "AND KIND OF EVENTS = " + nameKindEvent + " WAS SEND" );
        return ResponseEntity.ok(readyToSendDTOs);
    }
}