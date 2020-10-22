package com.hub.sensitivefield.repository;

import com.hub.sensitivefield.model.TypeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeEventRepository extends JpaRepository<TypeEvent, Integer> {

    Optional<TypeEvent> findByName(String name);
}
