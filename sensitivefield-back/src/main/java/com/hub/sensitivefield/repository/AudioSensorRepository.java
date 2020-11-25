package com.hub.sensitivefield.repository;

import com.hub.sensitivefield.model.AudioSensor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AudioSensorRepository extends JpaRepository<AudioSensor, Integer> {

    Optional<AudioSensor> getByName(String name);

    List<AudioSensor> findAllByTimeStampAfterAndTimeStampBeforeAndNameContains(LocalDateTime after, LocalDateTime before, String name);
//    List<AudioSensor> findAllByNameContains(String name, Pageable pageable);

    List<AudioSensor> findAll(Sort timeStamp, Pageable pageable);
}