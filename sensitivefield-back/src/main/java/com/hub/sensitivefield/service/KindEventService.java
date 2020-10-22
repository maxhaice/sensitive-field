package com.hub.sensitivefield.service;

import com.hub.sensitivefield.DTO.KindEventDTO;
import com.hub.sensitivefield.DTO.newDTO.newKindEventDTO;
import com.hub.sensitivefield.model.KindEvent;
import com.hub.sensitivefield.model.PriorityEvent;
import com.hub.sensitivefield.model.TypeEvent;
import com.hub.sensitivefield.repository.KindEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KindEventService {

    @Autowired
    private TypeEventService typeEventService;

    @Autowired
    private KindEventRepository kindEventRepository;

    public List<KindEvent> getAll(){
        return kindEventRepository.findAll();
    }

    public Optional<KindEvent> getByName(String name){
        return Optional.ofNullable(kindEventRepository.findByName(name));
    }

    public List<KindEvent> getAllByPriority(PriorityEvent priorityEvent){
        return kindEventRepository.findAllByPriority(priorityEvent.name());
    }

    public boolean deleteById(int id){
        Optional<KindEvent> optionalKindEvent = kindEventRepository.findById(id);
        if(optionalKindEvent.isEmpty()){
            return false;
        }
        else{
            kindEventRepository.deleteById(id);
            return true;
        }
    }

    public boolean saveKindEvent(newKindEventDTO newKindEventDTO){
        Optional<TypeEvent> optionalTypeEvent = typeEventService.getTypeEventByName(newKindEventDTO.getTypeEvent());
        if(optionalTypeEvent.isPresent()){
            KindEvent kindEvent = new KindEvent();
            kindEvent.setName(newKindEventDTO.getName());
            kindEvent.setPriority(PriorityEvent.valueOf(newKindEventDTO.getPriorityEvent()));
            kindEvent.setTypeEvent(optionalTypeEvent.get());
            kindEventRepository.save(kindEvent);
            return true;
        }
        else{
            return false;
        }
    }

    public KindEventDTO convertToDTO(KindEvent kindEvent){
        return new KindEventDTO(kindEvent.getId(), kindEvent.getName(), kindEvent.getTypeEvent(), kindEvent.getPriority());
    }

    public KindEvent convertFromDTO(newKindEventDTO newKindEventDTO){
        Optional<TypeEvent> optionalTypeEvent = typeEventService.getTypeEventByName(newKindEventDTO.getTypeEvent());
        TypeEvent typeEvent = null;
        if(optionalTypeEvent.isEmpty()){
            typeEventService.saveTypeEvent(newKindEventDTO.getTypeEvent());
            typeEvent = typeEventService.getTypeEventByName(newKindEventDTO.getName()).get();
        }
        return new KindEvent(newKindEventDTO.getPriorityEvent(), newKindEventDTO.getName(), typeEvent);
    }


}
