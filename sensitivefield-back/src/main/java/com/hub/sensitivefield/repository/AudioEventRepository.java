package com.hub.sensitivefield.repository;

import com.hub.sensitivefield.model.AudioEvent;
import com.hub.sensitivefield.model.AudioSensor;
import com.hub.sensitivefield.model.KindEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AudioEventRepository extends JpaRepository<AudioEvent, Integer> {
    Page<AudioEvent> findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndAudioSensorEquals(LocalDateTime dateBefore,
                                                                                                     LocalDateTime dateAfter,
                                                                                                     AudioSensor audioSensor,
                                                                                                     Pageable pageable);
    Page<AudioEvent> findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndAudioSensorEqualsAndKindEvent_Priority(LocalDateTime dateBefore,
                                                                                                       LocalDateTime dateAfter,
                                                                                                       AudioSensor audioSensor,
                                                                                                       String priority,
                                                                                                       Pageable pageable);

    Page<AudioEvent> findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndAudioSensorEqualsAndKindEvent_TypeEvent_Name(LocalDateTime dateBefore,
                                                                                                                       LocalDateTime dateAfter,
                                                                                                                       AudioSensor audioSensor,
                                                                                                                       String typeEvent,
                                                                                                                       Pageable pageable);
    Page<AudioEvent> findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndAudioSensorEqualsAndKindEvent_TypeEvent_NameAndKindEvent_Priority(LocalDateTime dateBefore,
                                                                                                                                  LocalDateTime dateAfter,
                                                                                                                                  AudioSensor audioSensor,
                                                                                                                                  String typeEvent,
                                                                                                                                  String priority,
                                                                                                                                  Pageable pageable);

    Page<AudioEvent> findAllByDateServerLessThanEqualAndDateServerGreaterThanEqual(LocalDateTime dateBefore,
                                                                                 LocalDateTime dateAfter,
                                                                                 Pageable pageable);

    Page<AudioEvent> findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndKindEvent_Priority(LocalDateTime dateBefore,
                                                                                   LocalDateTime dateAfter,
                                                                                   String priority,
                                                                                   Pageable pageable);

    Page<AudioEvent> findAll(Pageable pageable);
    Page<AudioEvent> findAllByKindEvent_Priority(String priority, Pageable pageable);

    Page<AudioEvent> findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndKindEvent_TypeEvent_Name(LocalDateTime dateBefore,
                                                                                                   LocalDateTime dateAfter,
                                                                                                   String typeEvent,
                                                                                                   Pageable pageable);
    Page<AudioEvent> findAllByDateServerLessThanEqualAndDateServerGreaterThanEqualAndKindEvent_TypeEvent_NameAndKindEvent_Priority(LocalDateTime dateBefore,
                                                                                                              LocalDateTime dateAfter,
                                                                                                              String typeEvent,
                                                                                                              String priority,
                                                                                                              Pageable pageable);
}