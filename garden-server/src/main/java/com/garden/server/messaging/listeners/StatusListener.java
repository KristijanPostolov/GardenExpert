package com.garden.server.messaging.listeners;

import com.garden.server.messaging.MessageMapper;
import com.garden.server.messaging.messages.HubStatusMessage;
import com.garden.server.service.HubStatusService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StatusListener extends MacPrefixTopicListener {

    private static final Logger log = LoggerFactory.getLogger(StatusListener.class);

    private final MessageMapper messageMapper;
    private final HubStatusService service;

    public StatusListener(MessageMapper messageMapper, HubStatusService service) {
        this.messageMapper = messageMapper;
        this.service = service;
    }

    @Override
    public void handleMessage(String mac, MqttMessage message) {
        messageMapper.fromJson(message.getPayload(), HubStatusMessage.class)
                .ifPresent(hubStatusMessage -> {
                    log.info("Status received for [{}]", mac);
                    service.updateForMac(mac, hubStatusMessage);
                });
    }
}
