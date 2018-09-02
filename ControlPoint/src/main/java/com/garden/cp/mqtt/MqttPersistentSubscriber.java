package com.garden.cp.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MqttPersistentSubscriber implements MqttCallbackExtended {

    private final Logger log = LoggerFactory.getLogger(MqttPersistentSubscriber.class);

    private final Map<String, IMqttMessageListener> subscriptions;
    private final MqttClient mqttClient;

    public MqttPersistentSubscriber(MqttClient client) {
        subscriptions = new HashMap<>();
        this.mqttClient = client;
    }

    public String getClientId() {
        return mqttClient.getClientId();
    }

    public void subscribe(String topic, IMqttMessageListener listener) {
        if(subscriptions.containsKey(topic)) {
            return;
        }
        log.info("Subscribing to [{}]", topic);
        subscriptions.put(topic, listener);
        try {
            mqttClient.subscribe(topic, listener);
        } catch (MqttException e) {
            log.error("Could not subscribe client [{}] to broker [{}]",
                    mqttClient.getClientId(), mqttClient.getServerURI());
        }
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        subscriptions.forEach((key, value) -> {
            try {
                mqttClient.subscribe(key, value);
            } catch (MqttException e) {
                log.error("Could not subscribe client [{}] to broker [{}]",
                        mqttClient.getClientId(), mqttClient.getServerURI());
            }
        });
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