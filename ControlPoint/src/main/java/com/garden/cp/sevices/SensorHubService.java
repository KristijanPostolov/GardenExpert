package com.garden.cp.sevices;

import com.garden.cp.mqtt.publishers.ConnectionPublisher;
import com.garden.cp.repository.SensorHubRepository;
import org.springframework.stereotype.Service;

@Service
public class SensorHubService {

    private final ConnectionPublisher publisher;
    private final SensorHubRepository repository;

    public SensorHubService(ConnectionPublisher publisher, SensorHubRepository repository) {
        this.publisher = publisher;
        this.repository = repository;
    }

    public void sensorHubConnected(String mac) {
        if(!repository.hasConfiguration(mac) || !repository.hasStatus(mac)) {
            publisher.publishConnection(mac);
        }
    }

}
