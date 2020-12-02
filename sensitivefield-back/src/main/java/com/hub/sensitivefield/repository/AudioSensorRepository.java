package com.hub.sensitivefield.repository;

import com.hub.sensitivefield.model.AudioSensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AudioSensorRepository extends JpaRepository<AudioSensor, Integer> {

    Optional<AudioSensor> getByName(String name);

    Page<AudioSensor> findAllByTimeStampLessThanEqualAndTimeStampGreaterThanEqualAndNameIsContaining(LocalDateTime before, LocalDateTime after, String name, Pageable pageable);//LocalDateTime after,

    Page<AudioSensor> findAllByNameIsContaining(String name, Pageable pageable);

    @Query(value = "SELECT * FROM audio_sensor",
            countQuery = "SELECT count(*) FROM audio_sensor",
            nativeQuery = true)
    Page<AudioSensor> findAll(Pageable pageable);
}