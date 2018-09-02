package com.garden.cp.mqtt.listeners;

import com.garden.cp.mqtt.MqttPersistentSubscriber;
import com.garden.cp.sevices.SensorHubService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConnectionListener extends MacPrefixTopicListener {

    private final SensorHubService service;
    private final StatusListener statusListener;

    public ConnectionListener(SensorHubService service,
                              StatusListener statusListener,
                              MqttPersistentSubscriber localSubscriber,
                              @Value("${local.topics.connection}") String topic) {
        this.service = service;
        this.statusListener = statusListener;
        localSubscriber.subscribe(topic, this);
    }

    @Override
    public void handleMessage(String mac, MqttMessage message) {
        statusListener.addSubscription(mac);
        // TODO: Config
        service.sensorHubConnected(mac);
    }
}
