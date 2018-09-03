package com.garden.cp.mqtt.publishers;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MqttTriggerPublisher {

    private static final Logger log = LoggerFactory.getLogger(MqttTriggerPublisher.class);

    private final MqttClient localClient;

    public MqttTriggerPublisher(MqttClient localMqttClient) {
        this.localClient = localMqttClient;
    }

    public void publish(String topic, boolean flag) {
        char message = flag ? '1' : '0';
        byte[] payload = { (byte)message };
        try {
            localClient.publish(topic, payload, 1, false);
        } catch (MqttException e) {
            log.error("Could not publish to [{}]", localClient.getClientId(), e);
        }
    }
}
