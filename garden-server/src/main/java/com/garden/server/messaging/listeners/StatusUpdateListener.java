package com.garden.server.messaging.listeners;

import com.garden.server.messaging.messages.HubStatusMessage;
import com.garden.server.service.HubStatusService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class StatusUpdateListener extends MacPrefixTopicListener {

    private static final Logger log = LoggerFactory.getLogger(StatusUpdateListener.class);

    private final Jackson2JsonObjectMapper objectMapper;
    private final HubStatusService service;

    public StatusUpdateListener(Jackson2JsonObjectMapper objectMapper, HubStatusService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }

    @Override
    public void handleMessage(String mac, MqttMessage message) {
        try {
            HubStatusMessage hubStatusMessage = objectMapper.fromJson(message.getPayload(), HubStatusMessage.class);
            log.info("Status update received for [{}]", mac);
            service.updateForMac(mac, hubStatusMessage);
        } catch (Exception e) {
            log.error("Could not deserialize message: [{}]", new String(message.getPayload()), e);
        }
    }

}
