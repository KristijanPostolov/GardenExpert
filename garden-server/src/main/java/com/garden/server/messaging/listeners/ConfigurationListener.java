package com.garden.server.messaging.listeners;

import com.garden.server.messaging.MessageMapper;
import com.garden.server.messaging.messages.HubConfigurationMessage;
import com.garden.server.messaging.messages.IdentifiedMessage;
import com.garden.server.service.HubConfigurationService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationListener extends MacPrefixTopicListener {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationListener.class);

    private final MessageMapper messageMapper;
    private final HubConfigurationService service;
    private final String clientId;

    public ConfigurationListener(MessageMapper messageMapper, HubConfigurationService service, MqttClient mqttClient,
                                 @Value("${mqtt.topics.configuration}") String topic) throws MqttException {
        this.messageMapper = messageMapper;
        this.service = service;
        this.clientId = mqttClient.getClientId();
        mqttClient.subscribe(topic, this);
    }

    @Override
    public void handleMessage(String mac, MqttMessage message) {
        messageMapper.fromJson(message.getPayload(), IdentifiedMessage.class)
                .filter(identifiedMessage -> !identifiedMessage.getClientId().equals(clientId))
                .map(identifiedMessage ->
                        messageMapper.fromJson(identifiedMessage.getContent(), HubConfigurationMessage.class))
                .ifPresent(optionalHubConfigurationMessage ->
                        optionalHubConfigurationMessage.ifPresent(hubConfigurationMessage -> {
                            log.info("Configuration received from [{}]", mac);
                            service.updateConfigurationForMacAddress(mac, hubConfigurationMessage);
                        }));
    }
}
