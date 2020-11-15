package com.hub.sensitivefield.service;

import com.hub.sensitivefield.dto.KindEventDTO;
import com.hub.sensitivefield.dto.newDTO.NewKindEventDTO;
import com.hub.sensitivefield.model.KindEvent;
import com.hub.sensitivefield.model.PriorityEvent;
import com.hub.sensitivefield.model.TypeEvent;
import com.hub.sensitivefield.repository.KindEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KindEventService {

    private final TypeEventService typeEventService;

    private final KindEventRepository kindEventRepository;

    public KindEventService(TypeEventService typeEventService, KindEventRepository kindEventRepository) {
        this.typeEventService = typeEventService;
        this.kindEventRepository = kindEventRepository;
    }

    public List<KindEvent> getAll(){
        return kindEventRepository.findAll();
    }

    public Optional<KindEvent> getByName(String name){
        return Optional.ofNullable(kindEventRepository.findByName(name));
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

    public boolean saveKindEvent(NewKindEventDTO newKindEventDTO){
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
            System.out.println("Mistake ERROR, when TypeEvent is ABSENT in DB");
            return false;
        }

    }

    public KindEventDTO convertToDTO(KindEvent kindEvent){
        return new KindEventDTO(kindEvent.getId(), kindEvent.getName(), kindEvent.getTypeEvent(), kindEvent.getPriority());
    }
}