package com.garden.cp.mqtt.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garden.cp.model.MeasurementMessage;
import com.garden.cp.mqtt.MqttPersistentSubscriber;
import com.garden.cp.mqtt.publishers.MeasurementPublisher;
import com.garden.cp.sevices.MeasurementService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class MeasurementListener extends MacPrefixTopicListener {

    private static final Logger log = LoggerFactory.getLogger(MeasurementListener.class);

    private final ObjectMapper objectMapper;
    private final MeasurementService service;
    private final MeasurementPublisher publisher;

    public MeasurementListener(ObjectMapper objectMapper, MeasurementService service,
                               MeasurementPublisher publisher, MqttPersistentSubscriber localSubscriber,
                               @Value("${local.topics.measurement}") String topic) {
        this.objectMapper = objectMapper;
        this.service = service;
        this.publisher = publisher;
        localSubscriber.subscribe(topic, this);
    }

    @Override
    public void handleMessage(String mac, MqttMessage message) {
        try {
            MeasurementMessage measurementMessage =
                    objectMapper.readValue(message.getPayload(), MeasurementMessage.class);
            log.info("Received [{}] from [{}]", measurementMessage.type, mac);
            measurementMessage.timestamp = LocalDateTime.now();
            service.handleMeasurement(mac, measurementMessage);
            publisher.publishMeasurement(mac, measurementMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
