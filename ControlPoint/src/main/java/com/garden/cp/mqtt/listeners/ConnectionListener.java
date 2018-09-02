package com.garden.cp.mqtt.listeners;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
public class ConnectionListener extends MacPrefixTopicListener {

    @Override
    public void handleMessage(String mac, MqttMessage message) {
        // TODO: Handle
    }
}
