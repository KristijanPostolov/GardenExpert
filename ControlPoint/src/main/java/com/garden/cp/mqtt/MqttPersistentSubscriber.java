package com.garden.cp.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MqttPersistentSubscriber implements MqttCallbackExtended {

    private final Logger log = LoggerFactory.getLogger(MqttPersistentSubscriber.class);

    private final Map<String, IMqttMessageListener> subscriptions;
    private final MqttClient localMqttClient;

    public MqttPersistentSubscriber(MqttClient client) {
        subscriptions = new HashMap<>();
        this.localMqttClient = client;
    }

    public void subscribe(String topic, IMqttMessageListener listener) {
        subscriptions.put(topic, listener);
        try {
            localMqttClient.subscribe(topic, listener);
        } catch (MqttException e) {
            log.error("Could not subscribe client [{}] to topic [{}]", localMqttClient.getClientId(), topic);
        }
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        subscriptions.forEach((key, value) -> {
            try {
                localMqttClient.subscribe(key, value);
            } catch (MqttException e) {
                log.error("Could not subscribe client [{}] to topic [{}]", localMqttClient.getClientId(), key);
            }
        });
        // TODO: Publish current status and config
    }

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}