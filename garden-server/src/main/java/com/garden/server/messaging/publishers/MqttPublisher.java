package com.garden.server.messaging.publishers;

import com.garden.server.messaging.MessageMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisher {

    private static final Logger log = LoggerFactory.getLogger(MqttPublisher.class);

    private final MqttClient mqttClient;
    private final MessageMapper messageMapper;

    public MqttPublisher(MqttClient mqttClient, MessageMapper messageMapper) {
        this.mqttClient = mqttClient;
        this.messageMapper = messageMapper;
    }

    public void publishRetained(String topic, Object message) {
        messageMapper.toJson(message)
                .ifPresent(json -> {
                    MqttMessage mqttMessage = new MqttMessage(json.getBytes());
                    mqttMessage.setRetained(true);
                    try {
                        log.info("Publishing MQTT message to [{}]", topic);
                        mqttClient.publish(topic, mqttMessage);
                    } catch (MqttException e) {
                        log.warn("Could not publish to [{}])", topic, e);
                    }
                });
    }

}
