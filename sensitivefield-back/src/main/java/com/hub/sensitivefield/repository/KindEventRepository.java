package com.hub.sensitivefield.repository;

import com.hub.sensitivefield.model.KindEvent;
import com.hub.sensitivefield.model.TypeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KindEventRepository extends JpaRepository<KindEvent, Integer> {

    KindEvent findByName(String name);

    List<KindEvent> findAllByPriority(String priority);

    List<KindEvent> findAllByTypeEvent(TypeEvent typeEvent);//TODO: for future
}