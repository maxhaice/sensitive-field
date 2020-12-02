package com.hub.sensitivefield.controller;

import com.hub.sensitivefield.dto.KindEventDTO;
import com.hub.sensitivefield.dto.newDTO.NewKindEventDTO;
import com.hub.sensitivefield.model.KindEvent;
import com.hub.sensitivefield.service.KindEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class KindEventController {

    private final KindEventService kindEventService;

    @Autowired
    public KindEventController(KindEventService kindEventService) {
        this.kindEventService = kindEventService;
    }

    @GetMapping("/api/kinds-events")
    public ResponseEntity<List<KindEventDTO>> getAllKindEvents() {
        return ResponseEntity.ok(kindEventService.getAll()
                .stream()
                .map(kindEventService::convertToDTO)
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/api/kinds-events/{id}/delete")
    public ResponseEntity<Void> deleteKindEvent(@PathVariable int id) {
        if (kindEventService.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/kinds-events/{name}")
    public ResponseEntity<KindEventDTO> getKindEventByName(@PathVariable String name) {
        Optional<KindEvent> optionalKindEvent = kindEventService.getByName(name);
        if (optionalKindEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(kindEventService.convertToDTO(optionalKindEvent.get()));
        }
    }

    @PostMapping("/api/kinds-events")
    public ResponseEntity<Void> saveKindEvents(@RequestBody NewKindEventDTO newKindEventDTO){
        if(kindEventService.saveKindEvent(newKindEventDTO)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}