package com.garden.server.messaging.listeners;

import com.garden.server.messaging.MessageMapper;
import com.garden.server.messaging.messages.HubStatusMessage;
import com.garden.server.messaging.messages.IdentifiedMessage;
import com.garden.server.service.HubStatusService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StatusListener extends MacPrefixTopicListener {

    private static final Logger log = LoggerFactory.getLogger(StatusListener.class);

    private final MessageMapper messageMapper;
    private final HubStatusService service;
    private final String clientId;

    public StatusListener(MessageMapper messageMapper, HubStatusService service, MqttClient mqttClient,
                          @Value("${mqtt.topics.status}") String topic) throws MqttException {
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
                        messageMapper.fromJson(identifiedMessage.getContent(), HubStatusMessage.class))
                .ifPresent(optionalHubStatusMessage -> optionalHubStatusMessage.ifPresent(hubStatusMessage -> {
                    log.info("Status received for [{}]", mac);
                    service.updateForMac(mac, hubStatusMessage);
                }));
    }
}
