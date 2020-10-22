package com.hub.sensitivefield.controller;

import com.hub.sensitivefield.service.SituationWebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SituationWebSocketController {

    private static Logger logger = LoggerFactory.getLogger(SituationWebSocketController.class);

    @Autowired
    private SituationWebSocketService situationWebSocketService;

    @MessageMapping("/get-last-events")
    private void getLastEvents(@Header("simpSessionId") String sesionId, Integer count) {
        logger.info("last events was send by socket to session with id=" + sesionId);
        situationWebSocketService.getLastEvents(sesionId, count);
    }

    @MessageMapping("/get-sensors")
    private void getSensors(@Header("simpSessionId") String sesionId) {
        logger.info("sensor was send by socket to session with id=" + sesionId);
        situationWebSocketService.getSensors(sesionId);
    }

    @MessageMapping("/get-sensor-state")
    private void getSensorState(@Header("simpSessionId") String sesionId, Integer id) {
        situationWebSocketService.getSensorState(sesionId, id);
    }
}
