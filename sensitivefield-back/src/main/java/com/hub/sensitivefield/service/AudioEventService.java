package com.hub.sensitivefield.service;

import com.hub.sensitivefield.dto.AudioEventDTO;
import com.hub.sensitivefield.intermediate.*;
import com.hub.sensitivefield.dto.newDTO.NewAudioEventDTO;
import com.hub.sensitivefield.dto.newDTO.NewKindEventDTO;
import com.hub.sensitivefield.valueobjects.ID;
import com.hub.sensitivefield.valueobjects.Latitude;
import com.hub.sensitivefield.valueobjects.Longitude;
import com.hub.sensitivefield.model.*;
import com.hub.sensitivefield.repository.AudioEventRepository;
import com.hub.sensitivefield.repository.AudioSensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AudioEventService {

    private final Logger logger = LoggerFactory.getLogger(AudioEventService.class);

    final private AudioEventRepository audioEventRepository;

    final private AudioSensorRepository audioSensorRepository;

    final private SituationWebSocketService situationWebSocketService;

    final private KindEventService kindEventService;

    final private TypeEventService typeEventService;

    final private AudioSensorService audioSensorService;

    @Autowired
    public AudioEventService(AudioSensorService audioSensorService, AudioEventRepository audioEventRepository, AudioSensorRepository audioSensorRepository, SituationWebSocketService situationWebSocketService, KindEventService kindEventService, TypeEventService typeEventService) {
        this.audioEventRepository = audioEventRepository;
        this.audioSensorRepository = audioSensorRepository;
        this.situationWebSocketService = situationWebSocketService;
        this.kindEventService = kindEventService;
        this.typeEventService = typeEventService;
        this.audioSensorService = audioSensorService;
    }

    public Optional<AudioEvent> getAudioEventById(int id) {
        return audioEventRepository.findById(id);
    }

    public List<AudioEvent> getSomeAudioEvents(int count) {
        return audioEventRepository.findAll().subList(0, count);
    }

    public List<AudioEvent> getAllAudioEvents() {
        return audioEventRepository.findAll();
    }

    public void saveAudioEvent(NewAudioEventDTO newAudioEventDTO) {
        Optional<AudioSensor> audioSensor = audioSensorRepository.findById(newAudioEventDTO.getSensorId());
        if(audioSensor.isEmpty()){
            Latitude latitude = new Latitude(newAudioEventDTO.getSensorCoordinates()
                    .get("lat").asDouble());
            Longitude longitude = new Longitude(newAudioEventDTO.getSensorCoordinates()
                    .get("lon").asDouble());
            ID id = new ID(newAudioEventDTO.getSensorId());
            AudioSensor newAudioSensorForSave = new AudioSensor(
                    id,
                    latitude,
                    longitude,
                    LocalDateTime.now());
            audioSensorRepository.save(newAudioSensorForSave);
            situationWebSocketService.sendNewSensor(audioSensorService.convertToDTO(newAudioSensorForSave));
        }
        AudioEvent audioEvent = convertFromDTO(newAudioEventDTO);
        audioEventRepository.save(audioEvent);

        List<AudioEvent> audioEvents = getAllAudioEvents();
        AudioEvent jsonEvent = audioEvents.get(audioEvents.size() - 1);
        jsonEvent.setDeleted(false);
        AudioEventDTO audioEventDTO = convertToDTO(jsonEvent);
        situationWebSocketService.sendNewEvent(audioEventDTO);
    }

    public boolean hideAudioEvent(int id) {
        Optional<AudioEvent> optionalAudioEvent = getAudioEventById(id);
        if (optionalAudioEvent.isEmpty()) return false;
        AudioEvent audioEvent = optionalAudioEvent.get();
        audioEvent.setDeleted(true);
        audioEventRepository.save(audioEvent);
        return true;
    }

    public AudioEvent convertFromDTO(NewAudioEventDTO newAudioEventDTO) {
        // it's okey because the sensor already has been added
        AudioSensor audioSensor = audioSensorRepository.findById(newAudioEventDTO.getSensorId()).get();

        LocalDateTime date_real = newAudioEventDTO.getDateReal();
        LocalDateTime dateServer = LocalDateTime.now().plusHours(2);;

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

    private KindEvent checkDefaultValueOfKindEvent(String typeSource1) {
        KindEvent kindEvent;
        Optional<KindEvent> optionalKindEvent = kindEventService.getByName(typeSource1);
        Optional<TypeEvent> typeEvent = typeEventService.getTypeEventByName("default");
        if(optionalKindEvent.isPresent()){
            kindEvent = optionalKindEvent.get();
        }
        else{
            if(typeEvent.isPresent()){
                kindEventService.saveKindEvent(new NewKindEventDTO(typeSource1, "default", PriorityEvent.Default));
                optionalKindEvent = kindEventService.getByName(typeSource1);
                kindEvent = optionalKindEvent.get();
                logger.warn("kindEvent hasn't value like a typeSource=" + typeSource1 + " and was set default value");
            }
            else{
                typeEventService.saveTypeEvent("default");
                kindEventService.saveKindEvent(new NewKindEventDTO(typeSource1, "default", PriorityEvent.Default));
                //its okey because this kind has been already added to db
                kindEvent = kindEventService.getByName(typeSource1).get();
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

    public static AudioEventWithoutSensor convertToDTOWithoutEvents(AudioEvent audioEvent) {
        return new AudioEventWithoutSensor(
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

    public Page<AudioEvent> getFilteredSortedPageableAudioEvents(
            LocalDateTime dateAfter,
            LocalDateTime dateBefore,
            Integer id,
            String typeEventName,
            String priority,
            String sortBy, boolean isDescending,
            Integer page, Integer pageSize) {
        if (sortBy != null) {
            sortBy = switch (sortBy) {//sort, default ascending
                case "date":
                    yield "dateReal";
                case "lat":
                    yield "latitude";
                case "lon":
                    yield "longitude";
                default:
                    throw new IllegalStateException("Unexpected value: " + sortBy);
            };
        }
        else{//default sorting by id
            sortBy = "id";
        }

        Pageable pageable;
        if(isDescending){
            pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        }
        else{
            pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));
        }

        Optional<AudioSensor> audioSensor = audioSensorRepository.findById(Objects.requireNonNullElse(id, -1));
        if(audioSensor.isEmpty() && typeEventName==null && dateAfter == null && dateBefore == null){
            return audioEventRepository.findAll(pageable);
        }

        if(dateAfter==null){
            dateAfter = LocalDateTime.now().minusYears(2000);
            System.out.println(dateAfter);
        }
        if(dateBefore==null){
            dateBefore = LocalDateTime.now();
            System.out.println(dateBefore);
        }

        if(audioSensor.isPresent()){
            if(typeEventName==null){
                if(priority == null){
                    return audioEventRepository.findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndAudioSensorEquals(
                            dateBefore, dateAfter, audioSensor.get(), pageable);
                }
                else{
                    return audioEventRepository.findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndAudioSensorEqualsAndKindEvent_Priority(
                            dateBefore, dateAfter, audioSensor.get(), priority, pageable);
                }
            }
            else{
                if(priority == null){
                    return audioEventRepository.findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndAudioSensorEqualsAndKindEvent_TypeEvent_Name(
                            dateBefore, dateAfter, audioSensor.get(), typeEventName, pageable);
                }
                else{
                    return audioEventRepository.findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndAudioSensorEqualsAndKindEvent_TypeEvent_NameAndKindEvent_Priority(
                            dateBefore, dateAfter, audioSensor.get(), typeEventName, priority, pageable);
                }
            }
        }
        else{
            if(typeEventName==null){
                if(priority == null){
                    return audioEventRepository.findAllByDateServerLessThanEqualAndDateServerGreaterThanEqual(
                            dateBefore, dateAfter, pageable);
                }
                else{
                    return audioEventRepository.findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndKindEvent_Priority(
                            dateBefore, dateAfter, priority, pageable);
                }
            }
            else{
                if(priority == null){
                    return audioEventRepository.findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndKindEvent_TypeEvent_Name(
                            dateBefore, dateAfter, typeEventName, pageable);
                }
                else{
                    return audioEventRepository.findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndKindEvent_TypeEvent_NameAndKindEvent_Priority(
                            dateBefore, dateAfter, typeEventName, priority, pageable);
                }
            }
        }
    }
}