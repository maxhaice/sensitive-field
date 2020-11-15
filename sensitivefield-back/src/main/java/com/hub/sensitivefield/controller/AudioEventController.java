package com.hub.sensitivefield.controller;

import com.hub.sensitivefield.dto.AudioEventDTO;
import com.hub.sensitivefield.dto.newDTO.NewAudioEventDTO;
import com.hub.sensitivefield.model.AudioEvent;
import com.hub.sensitivefield.service.AudioEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/api/audio-events")
@CrossOrigin(origins = "http://localhost:4200")
public class AudioEventController {

    private static final Logger logger = LoggerFactory.getLogger(AudioEventController.class);

    private final AudioEventService audioEventService;

    @Autowired
    public AudioEventController(AudioEventService audioEventService) {
        this.audioEventService = audioEventService;
    }

    @GetMapping("/api/audio-events/")
    private ResponseEntity<List<AudioEventDTO>> getAllAudioEvents() {
        logger.info("AudioEvents was send");
        return ResponseEntity.ok(audioEventService.getAllAudioEvents()
                .stream().map(AudioEventService::convertToDTO).collect(Collectors.toList()));
    }

    @PutMapping("/api/audio-events/{id}/hide")
    private ResponseEntity<Void> hideAudioEvent(@PathVariable int id) {
        if (audioEventService.hideAudioEvent(id)) {
            logger.info("AudioEvent with id = " + id + " wasn't found and can NOT be HIDDEN");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("AudioEvent with id = " + id + " has HIDDEN");
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/api/audio-events/{id}/next")
    private ResponseEntity<AudioEventDTO> getNextAudioEvent(@PathVariable int id) {
        Optional<AudioEvent> optionalAudioEvent = audioEventService.getAudioEventById(id);
        if (optionalAudioEvent.isEmpty()) {
            logger.info("AudioEvent with id = " + id + " wasn't found");
            return ResponseEntity.noContent().build();
        }
        AudioEvent tempAudioEvent = optionalAudioEvent.get();

        List<AudioEvent> audioEvents = audioEventService.getAllAudioEvents()
                .stream().filter(audioEvent -> audioEvent.getDateReal()
                        .isAfter(tempAudioEvent.getDateReal()))
                .sorted(Comparator.comparing(AudioEvent::getDateReal))
                .collect(Collectors.toList());

        int index = 0;
        for (int i = 0; audioEvents.iterator().hasNext(); i++) {
            if (audioEvents.iterator().next().getId() == tempAudioEvent.getId()) {
                index = i;
                break;
            }
        }

        logger.info("AudioEvent with id = " + (id + 1) + " WAS SEND");
        return ResponseEntity.ok(AudioEventService.convertToDTO(audioEvents.get(index)));
    }

    @GetMapping("/api/audio-events/{id}/previous")
    private ResponseEntity<AudioEventDTO> getPreviousAudioEvent(@PathVariable int id) {
        Optional<AudioEvent> optionalAudioEvent = audioEventService.getAudioEventById(id);
        if (optionalAudioEvent.isEmpty()) {
            logger.info("AudioEvent with id = " + id + " WASN'T FOUND");
            return ResponseEntity.noContent().build();
        }
        AudioEvent tempAudioEvent = optionalAudioEvent.get();

        List<AudioEvent> audioEvents = audioEventService.getAllAudioEvents()
                .stream()
                .filter(audioEvent -> audioEvent.getDateReal()
                        .isBefore(tempAudioEvent.getDateReal()))
                .sorted((x, y) -> y.getDateReal().compareTo(x.getDateReal()) * -1)
                .collect(Collectors.toList());

        int index = 0;
        for (int i = 0; audioEvents.iterator().hasNext(); i++) {
            if (audioEvents.iterator().next().getId() == tempAudioEvent.getId()) {
                index = i;
                break;
            }
        }

        logger.info("AudioEvent with id = " + (id + 1) + " WAS SEND");
        return ResponseEntity.ok(AudioEventService.convertToDTO(audioEvents.get(index)));
    }

    @GetMapping("/api/audio-events/head")
    private ResponseEntity<List<AudioEventDTO>> getSomeAudioEvents(@RequestParam int count) {
        logger.info("AudioEvent in count = " + count + " WAS SEND");
        return ResponseEntity.ok(audioEventService.getSomeAudioEvents(count)
                .stream().map(AudioEventService::convertToDTO).collect(Collectors.toList()));
    }

    @PostMapping("/api/audio-events/")
    private ResponseEntity<?> addAudioEvent(@RequestBody NewAudioEventDTO newAudioEventDTO) {
        System.out.println("WTF1");
        if (audioEventService.saveAudioEvent(newAudioEventDTO)) {
            logger.info("AudioEvent was save");
            System.out.println("wtf2");
            return ResponseEntity.ok().build();
        } else {
            System.out.println("wtf3");
            logger.info("AudioEvent wasn't save because sensor with id = "
                    + newAudioEventDTO.getSensorId() + " WASN'T FOUND");
            return ResponseEntity.ok("New sensor was added");
        }
    }
}