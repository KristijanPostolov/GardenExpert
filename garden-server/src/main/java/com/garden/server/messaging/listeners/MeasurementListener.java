package com.garden.server.messaging.listeners;

import com.garden.server.messaging.messages.MeasurementMessage;
import com.garden.server.service.MeasurementService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.stereotype.Service;


@Service
public class MeasurementListener extends MacPrefixTopicListener {

    private static final Logger log = LoggerFactory.getLogger(MeasurementListener.class);

    private final Jackson2JsonObjectMapper objectMapper;
    private final MeasurementService service;

    public MeasurementListener(Jackson2JsonObjectMapper objectMapper, MeasurementService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }

    @Override
    public void handleMessage(String mac, MqttMessage message) {
        try {
            MeasurementMessage measurementMessage =
                    objectMapper.fromJson(message.getPayload(), MeasurementMessage.class);
            log.info("Saving [{}] measurement from [{}]", measurementMessage.type, mac);
            service.addMeasurement(mac, measurementMessage);
        } catch (Exception e) {
            log.error("Could not deserialize message: [{}]", new String(message.getPayload()), e);
        }
    }
}
