package com.hub.sensitivefield.controller;

import com.hub.sensitivefield.dto.TypeEventDTO;
import com.hub.sensitivefield.model.TypeEvent;
import com.hub.sensitivefield.service.TypeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TypeEventController {

    private final TypeEventService typeEventService;

    @Autowired
    public TypeEventController(TypeEventService typeEventService) {
        this.typeEventService = typeEventService;
    }

    @GetMapping("/api/types-events")
    public ResponseEntity<List<TypeEventDTO>> getAllTypeOfEvents() {
        return ResponseEntity.ok(typeEventService.getAll()
                .stream()
                .map(typeEvent -> new TypeEventDTO(typeEvent.getId(), typeEvent.getName()))
                .collect(Collectors.toList()));
    }

    @GetMapping("/api/types-events/{name}/")
    public ResponseEntity<TypeEvent> getTypeEventByName(@PathVariable String name) {
        Optional<TypeEvent> typeEventOptional = typeEventService.getTypeEventByName(name);
        if (typeEventOptional.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(typeEventOptional.get());
        }
    }

    @DeleteMapping("/api/types-events/{id}")
    public ResponseEntity<Void> deleteTypeEventById(@PathVariable int id) {
        if (typeEventService.deleteTypeEvent(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/types-events")
    public ResponseEntity<Void> saveTypeEvent(@RequestParam String name) {
        typeEventService.saveTypeEvent(name);
        return ResponseEntity.ok().build();
    }
}