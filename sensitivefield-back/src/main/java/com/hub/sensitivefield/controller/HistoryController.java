package com.hub.sensitivefield.controller;

import com.hub.sensitivefield.DTO.AudioEventDTO;
import com.hub.sensitivefield.DTO.AudioEventDTOwithoutSensor;
import com.hub.sensitivefield.DTO.AudioSensorDTOwithEvents;
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

    @Autowired
    private AudioEventService audioEventService;

    @Autowired
    private AudioSensorService audioSensorService;

    @GetMapping("/{id}")
    private ResponseEntity<AudioEventDTO> getAudioEventById(@PathVariable int id) {
        Optional<AudioEvent> audioEvent = audioEventService.getAudioEventById(id);
        if (audioEvent.isEmpty()) {
            logger.info("History event with this id=" + id + " WASN'T FOUND");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("History with id=" + id + " WAS SEND");
            return ResponseEntity.ok(
                    AudioEventService.convertToDTO(audioEvent.get())
            );
        }
    }

    @GetMapping("/")
    private ResponseEntity<List<AudioSensorDTOwithEvents>> getEventByDate(@RequestParam(name = "date1") String date1Text,
                                                                          @RequestParam(name = "date2") String date2Text,
                                                                          @RequestParam List<String> nameKindEvent) {
        LocalDateTime localDate1 = LocalDateTime.parse(date1Text,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime localDate2 = LocalDateTime.parse(date2Text,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        List<AudioSensorDTOwithEvents> audioSensorsDTOwithEvents = audioSensorService.getAllAudioSensorEntity()
                .stream()
                .map(audioSensor -> audioSensorService.convertToDTOwithEvents(audioSensor))
                .collect(Collectors.toList());


        List<AudioSensorDTOwithEvents> readyToSendDTOs = new ArrayList<>();
        for (AudioSensorDTOwithEvents audioSensorDTOwithEvents : audioSensorsDTOwithEvents) {
            List<AudioEventDTOwithoutSensor> audioEventDTOS = audioSensorDTOwithEvents.getAudioEventDTOList();
            List<AudioEventDTOwithoutSensor> readyToAddToSensor = new ArrayList<>();

            for (AudioEventDTOwithoutSensor audioEventDTO : audioEventDTOS) {
                for (String s : nameKindEvent) {
                    if (audioEventDTO.getKindEvent().equals(s)) {
                        readyToAddToSensor.add(audioEventDTO);
                        break; // when we found kind event in event
                    }
                }
            }

            if (readyToAddToSensor.stream().count()!=0) {
                audioSensorDTOwithEvents.setAudioEventDTOList(readyToAddToSensor);
                readyToSendDTOs.add(audioSensorDTOwithEvents);
            }
        }



        if(localDate1.isAfter(localDate2)){//FIRST DATE IS AFTER DATE2
            return ResponseEntity.badRequest().build();
        }
        logger.info("History by this date1=" + date1Text + " date2=" + date2Text + "AND KIND OF EVENTS = " + nameKindEvent + " WAS SEND" );
        return ResponseEntity.ok(readyToSendDTOs);
    }
}
