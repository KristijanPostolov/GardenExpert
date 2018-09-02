package com.garden.cp.mqtt.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garden.cp.model.HubConfigurationMessage;
import com.garden.cp.model.IdentifiedMessage;
import com.garden.cp.mqtt.MqttPersistentSubscriber;
import com.garden.cp.sevices.ConfigurationService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConfigurationListener extends MacPrefixTopicListener{

    private static final Logger log = LoggerFactory.getLogger(ConfigurationListener.class);

    private final ObjectMapper objectMapper;
    private final ConfigurationService service;
    private final MqttPersistentSubscriber remoteSubscriber;
    private final String topicTemplate;


    public ConfigurationListener(ObjectMapper objectMapper, ConfigurationService service,
                                 MqttPersistentSubscriber remoteSubscriber,
                                 @Value("${remote.topics.configuration}") String topicTemplate) {
        this.objectMapper = objectMapper;
        this.service = service;
        this.remoteSubscriber = remoteSubscriber;
        this.topicTemplate = topicTemplate;
    }

    @Override
    public void handleMessage(String mac, MqttMessage message) {
        try {
            IdentifiedMessage identifiedMessage = objectMapper.readValue(message.getPayload(), IdentifiedMessage.class);
            if(remoteSubscriber.getClientId().equals(identifiedMessage.getClientId())) {
                return;
            }
            HubConfigurationMessage hubConfigurationMessage =
                    identifiedMessage.getContent(HubConfigurationMessage.class);
            service.updateConfiguration(mac, hubConfigurationMessage);
        } catch (IOException e) {
            log.error("Could not deserialize status message");
        }
    }

    public void addSubscription(String mac) {
        String topic = topicTemplate.replace("+", mac);
        remoteSubscriber.subscribe(topic, this);
    }
}
