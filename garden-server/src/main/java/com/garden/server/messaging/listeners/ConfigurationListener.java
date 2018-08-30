package com.garden.server.messaging.listeners;

import com.garden.server.messaging.MessageMapper;
import com.garden.server.messaging.messages.HubConfigurationMessage;
import com.garden.server.service.HubConfigurationService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationListener extends MacPrefixTopicListener {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationListener.class);

    private final MessageMapper messageMapper;
    private final HubConfigurationService service;

    public ConfigurationListener(MessageMapper messageMapper, HubConfigurationService service) {
        this.messageMapper = messageMapper;
        this.service = service;
    }

    @Override
    public void handleMessage(String mac, MqttMessage message) {
        messageMapper.fromJson(message.getPayload(), HubConfigurationMessage.class)
                .ifPresent(hubConfigurationMessage -> {
                    log.info("Configuration received from [{}]", mac);
                    service.updateConfigurationForMacAddress(mac, hubConfigurationMessage);
                });
    }
}
