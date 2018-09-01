package com.garden.server.service;

import com.garden.server.messaging.messages.HubStatusMessage;
import com.garden.server.messaging.publishers.HubStatusPublisher;
import com.garden.server.model.HubStatus;
import com.garden.server.model.SensorHub;
import com.garden.server.repository.HubStatusRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:default_hub_status.yml")
public class HubStatusService {

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
        publisher.publish(hubStatus);
        return repository.save(hubStatus);
    }

    @Transactional
    public HubStatus updateForMac(String mac, HubStatusMessage message) {
        HubStatus hubStatus = repository.findBySensorHub_MacAddress(mac);
        if(message.isSame(hubStatus)) {
            return hubStatus;
        }

        hubStatus.setHeaterActive(message.heaterActive);
        hubStatus.setSprinklerActive(message.sprinklerActive);
        publisher.publish(mac, message);
        return hubStatus;
    }

}
