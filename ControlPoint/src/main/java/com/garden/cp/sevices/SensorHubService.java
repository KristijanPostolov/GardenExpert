package com.garden.cp.sevices;

import com.garden.cp.mqtt.publishers.ConnectionPublisher;
import org.springframework.stereotype.Service;

@Service
public class SensorHubService {

    private final ConnectionPublisher publisher;

    public SensorHubService(ConnectionPublisher publisher) {
        this.publisher = publisher;
    }

    public void sensorHubConnected(String mac) {
        publisher.publishConnection(mac);
    }

}
