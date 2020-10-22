package com.hub.sensitivefield.repository;

import com.hub.sensitivefield.model.AudioEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioEventRepository extends JpaRepository<AudioEvent, Integer> {
}