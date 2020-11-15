package com.hub.sensitivefield.repository;

import com.hub.sensitivefield.model.AudioSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AudioSensorRepository extends JpaRepository<AudioSensor, Integer> {

    Optional<AudioSensor> getByName(String name);
}