package com.garden.cp.mqtt.publishers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttPublisher {

    private static final Logger log = LoggerFactory.getLogger(MqttPublisher.class);

    private final ObjectMapper objectMapper;
    private final MqttClient mqttClient;

    public MqttPublisher(ObjectMapper objectMapper, MqttClient mqttClient) {
        this.objectMapper = objectMapper;
        this.mqttClient = mqttClient;
    }

    public void publishRetained(String topic, Object message) {
        this.publish(topic, message, true);
    }

    public void publish(String topic, Object message, boolean retained) {
        try {
            String json = objectMapper.writeValueAsString(message);
            MqttMessage mqttMessage = new MqttMessage(json.getBytes());
            mqttMessage.setRetained(retained);
            mqttClient.publish(topic, mqttMessage);
        } catch (JsonProcessingException e) {
            log.error("Could not serialize message for topic [{}]", topic, e);
        } catch (MqttException e) {
            log.error("Could not publish message from client [{}]", mqttClient.getClientId(), e);
        }
    }
}
