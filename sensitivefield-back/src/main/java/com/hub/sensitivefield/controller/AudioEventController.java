package com.hub.sensitivefield.controller;

import com.hub.sensitivefield.dto.AudioEventDTO;
import com.hub.sensitivefield.dto.AudioEventPaginateDTOs;
import com.hub.sensitivefield.dto.newDTO.NewAudioEventDTO;
import com.hub.sensitivefield.model.AudioEvent;
import com.hub.sensitivefield.service.AudioEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/api/audio-events")
    private ResponseEntity<?> getFilteredAudioSensors(@RequestParam(required = false) Integer page,
                                                                            @RequestParam(required = false) Integer pageSize,
                                                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                                            @RequestParam(required = false)
                                                                                    LocalDateTime dateAfter,
                                                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                                            @RequestParam(required = false)
                                                                                    LocalDateTime dateBefore,
                                                                            @RequestParam(required = false)
                                                                            Integer id,
                                                                            @RequestParam(required = false)
                                                                            String kindEventName,
                                                                            @RequestParam(required = false)
                                                                            String priority,
                                                                            @RequestParam(required = false) String sortBy,
                                                                            @RequestParam(required = false) boolean isDescending
    ) {
        if(page==null && pageSize==null){
            return ResponseEntity.ok(audioEventService.getAllAudioEvents());
        }
        Page<AudioEvent> audioEvents = audioEventService
                .getFilteredSortedPageableAudioEvents(dateAfter, dateBefore, id, kindEventName, priority, sortBy, isDescending, page, pageSize);

        int totalPages = audioEvents.getTotalPages();


        List<AudioEventDTO> audioEventDTOs = audioEvents.stream()
                .map(AudioEventService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AudioEventPaginateDTOs(audioEventDTOs, totalPages));
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

    @RequestMapping(value = "/api/audio-events", method = RequestMethod.POST)
    private ResponseEntity<?> addAudioEvent(@RequestBody NewAudioEventDTO newAudioEventDTO) {
            audioEventService.saveAudioEvent(newAudioEventDTO);
            logger.info("AudioEvent was save");
            return ResponseEntity.ok("New audio event was added");
    }
}