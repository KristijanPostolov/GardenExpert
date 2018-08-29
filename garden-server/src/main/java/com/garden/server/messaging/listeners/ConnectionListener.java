package com.garden.server.messaging.listeners;

import com.garden.server.service.SensorHubService;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConnectionListener implements IMqttMessageListener {

    private static final Logger log = LoggerFactory.getLogger(ConnectionListener.class);

    private final SensorHubService service;

    public ConnectionListener(SensorHubService service) {
        this.service = service;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String mac = topic.replaceAll("/.*", "");
        log.debug("MQTT message received on [{}]", topic);
        log.info("Connected sensor hub [{}]", mac);
        service.getOrCreateByMac(mac);
    }

}