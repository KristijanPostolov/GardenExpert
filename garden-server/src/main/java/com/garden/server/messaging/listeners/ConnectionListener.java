package com.garden.server.messaging.listeners;

import com.garden.server.messaging.publishers.HubConfigurationPublisher;
import com.garden.server.messaging.publishers.HubStatusPublisher;
import com.garden.server.model.SensorHub;
import com.garden.server.service.SensorHubService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConnectionListener extends MacPrefixTopicListener {

    private static final Logger log = LoggerFactory.getLogger(ConnectionListener.class);

    private final SensorHubService service;
    private final HubStatusPublisher hubStatusPublisher;
    private final HubConfigurationPublisher hubConfigurationPublisher;

    public ConnectionListener(SensorHubService service, HubStatusPublisher hubStatusPublisher,
                              HubConfigurationPublisher hubConfigurationPublisher, MqttClient mqttClient,
                              @Value("${mqtt.topics.connection}") String topic) throws MqttException {
        this.service = service;
        this.hubStatusPublisher = hubStatusPublisher;
        this.hubConfigurationPublisher = hubConfigurationPublisher;
        mqttClient.subscribe(topic, this);
    }

    @Override
    public void handleMessage(String mac, MqttMessage message) {
        log.info("Connected sensor hub [{}]", mac);
        SensorHub sensorHub = service.getOrCreateByMac(mac);
        hubStatusPublisher.publish(sensorHub.getHubStatus());
        hubConfigurationPublisher.publish(sensorHub.getHubConfiguration());
    }

}
