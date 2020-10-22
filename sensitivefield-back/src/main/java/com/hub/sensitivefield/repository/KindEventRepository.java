package com.hub.sensitivefield.repository;

import com.hub.sensitivefield.model.KindEvent;
import com.hub.sensitivefield.model.PriorityEvent;
import com.hub.sensitivefield.model.TypeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KindEventRepository extends JpaRepository<KindEvent, Integer> {
    public KindEvent findByName(String name);
    public List<KindEvent> findAllByPriority (String priority);
    public List<KindEvent> findAllByTypeEvent(TypeEvent typeEvent);
}
