package com.garden.cp.mqtt.publishers;

import com.garden.cp.model.HubStatusMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StatusPublisher {

    private final MqttPublisher publisher;
    private final String topicTemplate;

    public StatusPublisher(MqttPublisher publisher, @Value("${remote.topics.status}") String topicTemplate) {
        this.publisher = publisher;
        this.topicTemplate = topicTemplate;
    }

    public void publishStatus(String mac, HubStatusMessage statusMessage) {
        String topic = topicTemplate.replace("+", mac);
        publisher.publishIdentified(topic, statusMessage, true);
    }

}
