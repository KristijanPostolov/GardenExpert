package com.garden.server.service;

import com.garden.server.messaging.messages.HubStatusMessage;
import com.garden.server.messaging.publishers.HubStatusPublisher;
import com.garden.server.model.HubStatus;
import com.garden.server.model.SensorHub;
import com.garden.server.repository.HubStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:default_hub_status.yml")
public class HubStatusService {

    private final Logger log = LoggerFactory.getLogger(HubStatusService.class);

    // Default configuration values
    private final boolean defaultHeaterActive;
    private final boolean defaultSprinklerActive;

    private final HubStatusRepository repository;
    private final HubStatusPublisher publisher;

    public HubStatusService(@Value("${heater-active}") Boolean defaultHeaterActive,
                            @Value("${sprinkler-active}") Boolean defaultSprinklerActive,
                            HubStatusRepository repository, HubStatusPublisher publisher) {
        this.defaultHeaterActive = defaultHeaterActive;
        this.defaultSprinklerActive = defaultSprinklerActive;
        this.repository = repository;
        this.publisher = publisher;
    }

    public HubStatus saveDefaultStatus(SensorHub sensorHub) {
        HubStatus hubStatus = new HubStatus(sensorHub, defaultHeaterActive, defaultSprinklerActive);
        log.info("Saving default status for sensor hub [{}]", sensorHub.getMacAddress());
        return repository.save(hubStatus);
    }

    @Transactional
    public HubStatus update(Long id, HubStatusMessage hubStatusMessage) {
        HubStatus status = repository.findBySensorHub_Id(id);
        log.info("Updating status for sensorHub [{}]", id);
        status.setHeaterActive(hubStatusMessage.heaterActive);
        status.setSprinklerActive(hubStatusMessage.sprinklerActive);
        publisher.publish(status);
        return repository.save(status);
    }

    @Transactional
    public HubStatus updateForMac(String mac, HubStatusMessage message) {
        HubStatus hubStatus = repository.findBySensorHub_MacAddress(mac);
        hubStatus.setHeaterActive(message.heaterActive);
        hubStatus.setSprinklerActive(message.sprinklerActive);
        publisher.publish(hubStatus);
        return hubStatus;
    }

}
