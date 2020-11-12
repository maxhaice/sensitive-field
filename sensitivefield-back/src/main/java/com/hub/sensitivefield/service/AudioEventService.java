package com.hub.sensitivefield.service;

import com.hub.sensitivefield.DTO.AudioEventDTO;
import com.hub.sensitivefield.DTO.AudioEventDTOwithoutSensor;
import com.hub.sensitivefield.DTO.newDTO.newAudioEventDTO;
import com.hub.sensitivefield.DTO.newDTO.newKindEventDTO;
import com.hub.sensitivefield.ValueObjects.ID;
import com.hub.sensitivefield.ValueObjects.Latitude;
import com.hub.sensitivefield.ValueObjects.Longitude;
import com.hub.sensitivefield.model.AudioEvent;
import com.hub.sensitivefield.model.AudioSensor;
import com.hub.sensitivefield.model.KindEvent;
import com.hub.sensitivefield.model.PriorityEvent;
import com.hub.sensitivefield.repository.AudioEventRepository;
import com.hub.sensitivefield.repository.AudioSensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AudioEventService {

    private final Logger logger = LoggerFactory.getLogger(AudioEventService.class);

    @Autowired
    private AudioEventRepository audioEventRepository;

    @Autowired
    private AudioSensorRepository audioSensorRepository;

    @Autowired
    private SituationWebSocketService situationWebSocketService;

    @Autowired
    private KindEventService kindEventService;

    public Optional<AudioEvent> getAudioEventById(int id) {
        return audioEventRepository.findById(id);
    }

    public List<AudioEvent> getSomeAudioEvents(int count) {
        return audioEventRepository.findAll().subList(0, count);
    }

    public void removeAllAudioEvents() {
        audioEventRepository.deleteAll();
    }

    public List<AudioEvent> getAllAudioEvents() {
        return audioEventRepository.findAll();
    }

    public boolean saveAudioEvent(newAudioEventDTO newAudioEventDTO) {
        Optional<AudioSensor> audioSensor = audioSensorRepository.findById(newAudioEventDTO.getSensor_id());
        if(audioSensor.isEmpty()){
            System.out.println("audiosensor is empty");
            Latitude latitude = new Latitude(newAudioEventDTO.getSensor_coordinates().get(0).asDouble());
            Longitude longitude = new Longitude(newAudioEventDTO.getSensor_coordinates().get(1).asDouble());
            ID id = new ID(newAudioEventDTO.getSensor_id());
            audioSensorRepository.save(new AudioSensor(id,
                     latitude,
                    longitude));
        }
        System.out.println("what'swrong");
        AudioEvent audioEvent = convertFromDTO(newAudioEventDTO);
        audioEventRepository.save(audioEvent);

        List<AudioEvent> audioEvents = getAllAudioEvents();
        AudioEvent jsonEvent = audioEvents.get(audioEvents.size() - 1);
        jsonEvent.setDeleted(false);
        AudioEventDTO audioEventDTO = convertToDTO(jsonEvent);
        situationWebSocketService.sendNewEvent(audioEventDTO);


        return audioSensor.isPresent();
    }

    public boolean hideAudioEvent(int id) {
        Optional<AudioEvent> optionalAudioEvent = getAudioEventById(id);

        if (optionalAudioEvent.isEmpty()) return false;
        AudioEvent audioEvent = optionalAudioEvent.get();
        audioEvent.setDeleted(true);
        audioEventRepository.save(audioEvent);
        return true;
    }

    public AudioEvent convertFromDTO(newAudioEventDTO newAudioEventDTO) {

        AudioSensor audioSensor = audioSensorRepository.findById(newAudioEventDTO.getSensor_id()).get();

        double latitude = newAudioEventDTO.getSensor_coordinates().get("lat").asDouble();
        double longitude = newAudioEventDTO.getSensor_coordinates().get("lon").asDouble();


        LocalDateTime date_real = newAudioEventDTO.getDate_real();
        LocalDateTime dateServer = LocalDateTime.now();

        double latitudeSource = newAudioEventDTO.getCoordinate()
                .get("lat").asDouble();
        double longitudeSource = newAudioEventDTO.getCoordinate()
                .get("lon").asDouble();

        String typeSource1 = "default";
        String typeSource2 = "default";
        String typeSource3 = "default";
        double persistenceSource1 = 0.0;
        double persistenceSource2 = 0.0;
        double persistenceSource3 = 0.0;
        try {
            String fieldName1 = newAudioEventDTO.getTypes().get(0).fieldNames().next();
            typeSource1 = fieldName1;
            persistenceSource1 = newAudioEventDTO.getTypes()
                    .get(0).get(fieldName1).asDouble();

            String fieldName2 = newAudioEventDTO.getTypes().get(1).fieldNames().next();
            typeSource2 = fieldName2;
            persistenceSource2 = newAudioEventDTO.getTypes()
                    .get(1).get(fieldName2).asDouble();

            String fieldName3 = newAudioEventDTO.getTypes().get(2).fieldNames().next();
            typeSource3 = fieldName3;
            persistenceSource3 = newAudioEventDTO.getTypes()
                    .get(2).get(fieldName3).asDouble();
        } catch (Exception e) {
            System.out.println("WARNING: One or more of three value was EMPTY");
        }
        KindEvent kindEvent = checkDefaultValueOfKindEvent(typeSource1);
        return new AudioEvent(
                audioSensor,
                latitudeSource,
                longitudeSource,
                dateServer,
                date_real,
                typeSource1,
                persistenceSource1,
                typeSource2,
                persistenceSource2,
                typeSource3,
                persistenceSource3,
                kindEvent
        );
    }

    private KindEvent checkDefaultValueOfKindEvent(String typeSource1){
        Optional<KindEvent> optionalKindEvent1 = kindEventService.getByName("default");
        KindEvent kindEvent = null;
        Optional<KindEvent> optionalKindEvent = kindEventService.getByName(typeSource1);
        System.out.println(typeSource1);
        if(optionalKindEvent.isPresent()){
            kindEvent = optionalKindEvent.get();
        }
        else{
            if(optionalKindEvent1.isPresent()){
                kindEvent = optionalKindEvent1.get();
                logger.warn("kindEvent hasn't value like a typeSource=" + typeSource1 + " and was set default value");
            }
            else{
                kindEventService.saveKindEvent(new newKindEventDTO("default", "default", PriorityEvent.Default));
                kindEvent = kindEventService.getByName("default").get();
                logger.warn("default value was add to db kindEvent with name=default");
            }
        }
        return kindEvent;
    }

    public static AudioEventDTO convertToDTO(AudioEvent audioEvent) {
        return new AudioEventDTO(
                audioEvent.getId(),
                audioEvent.getAudioSensor().getId(),
                audioEvent.getAudioSensor().getLatitude(),
                audioEvent.getAudioSensor().getLongitude(),
                audioEvent.getLatitude(),
                audioEvent.getLongitude(),
                audioEvent.getDateServer(),
                audioEvent.getDateReal(),
                audioEvent.getTypeSource1(),
                audioEvent.getPersistenceSource1(),
                audioEvent.getTypeSource2(),
                audioEvent.getPersistenceSource2(),
                audioEvent.getTypeSource3(),
                audioEvent.getPersistenceSource3(),
                audioEvent.isDeleted(),
                audioEvent.getKindEvent()
        );
    }

    public static AudioEventDTOwithoutSensor convertToDTOWithoutEvents(AudioEvent audioEvent) {
        return new AudioEventDTOwithoutSensor(
                audioEvent.getId(),
                audioEvent.getDateServer(),
                audioEvent.getDateReal(),
                audioEvent.getTypeSource1(),
                audioEvent.getPersistenceSource1(),
                audioEvent.getTypeSource2(),
                audioEvent.getPersistenceSource2(),
                audioEvent.getTypeSource3(),
                audioEvent.getPersistenceSource3(),
                audioEvent.isDeleted(),
                audioEvent.getKindEvent()
        );
    }
}
