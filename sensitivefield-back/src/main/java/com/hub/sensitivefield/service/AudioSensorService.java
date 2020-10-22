package com.hub.sensitivefield.service;

import com.hub.sensitivefield.DTO.AudioSensorDTO;
import com.hub.sensitivefield.DTO.AudioSensorDTOwithEvents;
import com.hub.sensitivefield.DTO.newDTO.newAudioSensorDTO;
import com.hub.sensitivefield.ValueObjects.ID;
import com.hub.sensitivefield.ValueObjects.Latitude;
import com.hub.sensitivefield.ValueObjects.Longitude;
import com.hub.sensitivefield.model.AudioSensor;
import com.hub.sensitivefield.repository.AudioSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AudioSensorService {

    @Autowired
    private AudioSensorRepository audioSensorRepository;

    public Optional<AudioSensor> getAudioSensorById(int id) {
        return audioSensorRepository.findById(id);
    }

    public List<AudioSensorDTO> getAllAudioSensors() {
        return audioSensorRepository.findAll()
                .stream().map(
                        this::convertToDTO
                ).collect(Collectors.toList());
    }

    public List<AudioSensor> getAllAudioSensorEntity(){
        return audioSensorRepository.findAll();
    }


    public AudioSensor getByName(String name){
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

    public void saveAudioSensor(newAudioSensorDTO newAudioSensorDTO) {
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

    public AudioSensorDTOwithEvents convertToDTOwithEvents(AudioSensor audioSensor) {
        return new AudioSensorDTOwithEvents(audioSensor.getId(),
                audioSensor.getName(),
                audioSensor.getLatitude(),
                audioSensor.getLongitude(),
                audioSensor.getAudioEvents()
                                                .stream()
                                                .map(AudioEventService::convertToDTOWithoutEvents)
                                                .collect(Collectors.toList()));
    }

    public AudioSensor convertFromDTO(newAudioSensorDTO newAudioSensorDTO) {
        ID id = new ID(newAudioSensorDTO.getId());
        Latitude latitude = new Latitude(newAudioSensorDTO.getLatitude());
        Longitude longitude = new Longitude(newAudioSensorDTO.getLongitude());
        return new AudioSensor(id, latitude, longitude);
    }

    public boolean changeAudioSensorName(int id, String name) {
        Optional<AudioSensor> audioSensorOptional = audioSensorRepository.findById(id);
        if(audioSensorOptional.isEmpty()){
            return false;
        }

        AudioSensor audioSensor = audioSensorOptional.get();
        audioSensor.setName(name);
        audioSensorRepository.save(audioSensor);
        return true;
    }
}
