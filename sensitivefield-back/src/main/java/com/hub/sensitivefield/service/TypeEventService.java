package com.hub.sensitivefield.service;

import com.hub.sensitivefield.model.TypeEvent;
import com.hub.sensitivefield.repository.TypeEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TypeEventService {

    private final TypeEventRepository typeEventRepository;

    @Autowired
    public TypeEventService(TypeEventRepository typeEventRepository) {
        this.typeEventRepository = typeEventRepository;
    }

    public List<TypeEvent> getAll() {
        return typeEventRepository.findAll();
    }

    public Optional<TypeEvent> getTypeEventByName(String name) {
        return typeEventRepository.findByName(name);
    }

    public boolean deleteTypeEvent(int id) {
        Optional<TypeEvent> optionalTypeEvent = typeEventRepository.findById(id);
        if (optionalTypeEvent.isEmpty()) {
            return false;
        } else {
            typeEventRepository.deleteById(id);
            return true;
        }
    }

    public void saveTypeEvent(String name) {
        TypeEvent typeEvent = new TypeEvent();
        typeEvent.setName(name);
        typeEvent.setKindEvent(new ArrayList<>());

        typeEventRepository.save(typeEvent);
    }
}