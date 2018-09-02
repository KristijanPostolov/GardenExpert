package com.garden.cp.mqtt.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garden.cp.model.HubStatusMessage;
import com.garden.cp.model.IdentifiedMessage;
import com.garden.cp.mqtt.MqttPersistentSubscriber;
import com.garden.cp.sevices.StatusService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StatusListener extends MacPrefixTopicListener {

    private static final Logger log = LoggerFactory.getLogger(StatusListener.class);

    private final ObjectMapper objectMapper;
    private final StatusService service;
    private final MqttPersistentSubscriber remoteSubscriber;
    private final String topicTemplate;

    public StatusListener(ObjectMapper objectMapper, StatusService service,
                          MqttPersistentSubscriber remoteSubscriber,
                          @Value("${remote.topics.status}") String topicTemplate) {
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
            HubStatusMessage hubStatusMessage = identifiedMessage.getContent(HubStatusMessage.class);
            service.updateStatus(mac, hubStatusMessage);
        } catch (IOException e) {
            log.error("Could not deserialize status message");
        }
    }

    public void addSubscription(String mac) {
        String topic = topicTemplate.replace("+", mac);
        log.info("Subscribing to [{}]", topic);
        remoteSubscriber.subscribe(topic, this);
    }

}
