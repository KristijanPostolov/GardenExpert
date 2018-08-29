package com.garden.server.messaging.listeners;

import com.garden.server.model.request.MeasurementRequest;
import com.garden.server.service.MeasurementService;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class MeasurementListener implements IMqttMessageListener {

    private static final Logger log = LoggerFactory.getLogger(MeasurementListener.class);

    private final Jackson2JsonObjectMapper jsonObjectMapper;
    private final MeasurementService service;

    public MeasurementListener(Jackson2JsonObjectMapper jsonObjectMapper, MeasurementService service) {
        this.jsonObjectMapper = jsonObjectMapper;
        this.service = service;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String mac = topic.replaceAll("/.*", "");
        String jsonPayload = new String(message.getPayload());
        MeasurementRequest measurementRequest = jsonObjectMapper.fromJson(jsonPayload, MeasurementRequest.class);

        log.debug("MQTT message received on [{}]", topic);
        log.info("Saving [{}] measurement from [{}]", measurementRequest.type, mac);
        service.addMeasurement(mac, measurementRequest);
    }
}
