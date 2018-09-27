package com.garden.cp.sevices;

import com.garden.cp.model.HubStatusMessage;
import com.garden.cp.mqtt.publishers.HeaterPublisher;
import com.garden.cp.mqtt.publishers.SprinklerPublisher;
import com.garden.cp.repository.SensorHubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private static final Logger log = LoggerFactory.getLogger(StatusService.class);

    private final SensorHubRepository repository;
    private final HeaterPublisher heaterPublisher;
    private final SprinklerPublisher sprinklerPublisher;

    public StatusService(SensorHubRepository repository, HeaterPublisher heaterPublisher,
                         SprinklerPublisher sprinklerPublisher) {
        this.repository = repository;
        this.heaterPublisher = heaterPublisher;
        this.sprinklerPublisher = sprinklerPublisher;
    }

    public void updateStatus(String mac, HubStatusMessage statusMessage) {
        log.info("Updating status for [{}]", mac);
        repository.putStatus(mac, statusMessage);
        heaterPublisher.publishHeaterStatus(mac, statusMessage.heaterActive);
        sprinklerPublisher.publishSprinklerStatus(mac, statusMessage.sprinklerActive);
    }

    public HubStatusMessage getStatus(String mac) {
        return repository.getStatus(mac);
    }

}
