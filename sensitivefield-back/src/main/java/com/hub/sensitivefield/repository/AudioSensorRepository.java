package com.hub.sensitivefield.repository;

import com.hub.sensitivefield.model.AudioSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioSensorRepository extends JpaRepository<AudioSensor, Integer> {
    public AudioSensor getByName(String name);
}
