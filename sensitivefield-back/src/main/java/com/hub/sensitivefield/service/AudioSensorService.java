package com.hub.sensitivefield.service;

import com.hub.sensitivefield.dto.AudioSensorDTO;
import com.hub.sensitivefield.dto.AudioSensorDTOWithEvents;
import com.hub.sensitivefield.dto.newDTO.NewAudioSensorDTO;
import com.hub.sensitivefield.valueobjects.ID;
import com.hub.sensitivefield.valueobjects.Latitude;
import com.hub.sensitivefield.valueobjects.Longitude;
import com.hub.sensitivefield.model.AudioSensor;
import com.hub.sensitivefield.repository.AudioSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AudioSensorService {

    private final AudioSensorRepository audioSensorRepository;

    @Autowired
    public AudioSensorService(AudioSensorRepository audioSensorRepository) {
        this.audioSensorRepository = audioSensorRepository;
    }

    public Optional<AudioSensor> getAudioSensorById(int id) {
        return audioSensorRepository.findById(id);
    }

    public List<AudioSensorDTO> getAllAudioSensors() {
        return audioSensorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AudioSensor> getAllAudioSensorEntity() {
        return audioSensorRepository.findAll();
    }

    //TODO: future fix
    public Optional<AudioSensor> getAudioSensorByName(String name){
        return audioSensorRepository.getByName(name);
    }

    public List<AudioSensor> getSomeAudioSensors(int count) {
        return audioSensorRepository.findAll().subList(0, count);
    }

    public boolean removeAudioSensorById(int id) {
        if (getAudioSensorById(id).isEmpty()) return false;
        audioSensorRepository.deleteById(id);
        return true;
    }

    public void saveAudioSensor(NewAudioSensorDTO newAudioSensorDTO) {
        audioSensorRepository.save(convertFromDTO(newAudioSensorDTO));
    }

    public boolean changeSensorCoordinates(int id, double latitude, double longitude) {
        Optional<AudioSensor> audioSensorOptional = getAudioSensorById(id);
        if (audioSensorOptional.isEmpty()) {
            return false;
        } else {
            AudioSensor audioSensor = audioSensorOptional.get();
            audioSensor.setLatitude(latitude);
            audioSensor.setLongitude(longitude);
            audioSensorRepository.save(audioSensor);
            return true;
        }
    }

    public AudioSensorDTO convertToDTO(AudioSensor audioSensor) {
        return new AudioSensorDTO(audioSensor.getId(), audioSensor.getLatitude()
                , audioSensor.getLongitude());
    }

    public AudioSensorDTOWithEvents convertToDTOwithEvents(AudioSensor audioSensor) {
        return new AudioSensorDTOWithEvents(audioSensor.getId(),
                audioSensor.getName(),
                audioSensor.getLatitude(),
                audioSensor.getLongitude(),
                audioSensor.getAudioEvents()
                        .stream()
                        .map(AudioEventService::convertToDTOWithoutEvents)
                        .collect(Collectors.toList()));
    }

    public AudioSensor convertFromDTO(NewAudioSensorDTO newAudioSensorDTO) {
        ID id = new ID(newAudioSensorDTO.getId());
        Latitude latitude = new Latitude(newAudioSensorDTO.getLatitude());
        Longitude longitude = new Longitude(newAudioSensorDTO.getLongitude());
        return new AudioSensor(id, latitude, longitude);
    }

    public boolean changeAudioSensorName(int id, String name) {
        Optional<AudioSensor> audioSensorOptional = audioSensorRepository.findById(id);
        if (audioSensorOptional.isEmpty()) {
            return false;
        }

        AudioSensor audioSensor = audioSensorOptional.get();
        audioSensor.setName(name);
        audioSensorRepository.save(audioSensor);
        return true;
    }
}