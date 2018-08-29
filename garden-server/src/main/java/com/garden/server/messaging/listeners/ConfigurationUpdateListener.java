package com.garden.server.messaging.listeners;

import com.garden.server.messaging.messages.HubConfigurationMessage;
import com.garden.server.service.HubConfigurationService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationUpdateListener extends MacPrefixTopicListener {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationUpdateListener.class);

    private final Jackson2JsonObjectMapper objectMapper;
    private final HubConfigurationService service;

    public ConfigurationUpdateListener(Jackson2JsonObjectMapper objectMapper, HubConfigurationService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }

    @Override
    public void handleMessage(String mac, MqttMessage message) {
        try {
            HubConfigurationMessage hubConfigurationMessage =
                    objectMapper.fromJson(message.getPayload(), HubConfigurationMessage.class);
            log.info("Configuration update received from [{}]", mac);
            service.updateConfigurationForMacAddress(mac, hubConfigurationMessage);
        } catch (Exception e) {
            log.error("Could not deserialize message: [{}]", new String(message.getPayload()), e);
        }
    }
}
