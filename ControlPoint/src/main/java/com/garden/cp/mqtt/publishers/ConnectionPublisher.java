package com.garden.cp.mqtt.publishers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConnectionPublisher {

    private final MqttPublisher publisher;
    private final String topicTemplate;

    public ConnectionPublisher(MqttPublisher publisher,
                               @Value("${remote.topics.connection}") String topicTemplate) {
        this.publisher = publisher;
        this.topicTemplate = topicTemplate;
    }

    public void publishConnection(String mac) {
        String topic = topicTemplate.replace("+", mac);
        publisher.publish(topic, "", false);
    }
}
