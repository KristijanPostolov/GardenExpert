package com.garden.server.messaging.listeners;

import com.garden.server.messaging.MessageMapper;
import com.garden.server.messaging.messages.MeasurementMessage;
import com.garden.server.service.MeasurementService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class MeasurementListener extends MacPrefixTopicListener {

    private static final Logger log = LoggerFactory.getLogger(MeasurementListener.class);

    private final MessageMapper messageMapper;
    private final MeasurementService service;

    public MeasurementListener(MessageMapper messageMapper, MeasurementService service, MqttClient mqttClient,
                               @Value("${mqtt.topics.measurement}") String topic) throws MqttException {
        this.messageMapper = messageMapper;
        this.service = service;
        mqttClient.subscribe(topic, this);
    }

    @Override
    public void handleMessage(String mac, MqttMessage message) {
        messageMapper.fromJson(message.getPayload(), MeasurementMessage.class)
                .ifPresent(measurementMessage -> {
                    log.info("Saving [{}] measurement from [{}]", measurementMessage.type, mac);
                    service.addMeasurement(mac, measurementMessage);
                });
    }
}
