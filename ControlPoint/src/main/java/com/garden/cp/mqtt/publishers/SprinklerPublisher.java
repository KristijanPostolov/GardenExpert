package com.garden.cp.mqtt.publishers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SprinklerPublisher {

    private final MqttTriggerPublisher publisher;
    private final String topicTemplate;

    public SprinklerPublisher(MqttTriggerPublisher publisher, @Value("${local.topics.sprinkler}") String topicTemplate) {
        this.publisher = publisher;
        this.topicTemplate = topicTemplate;
    }

    public void publishSprinklerStatus(String mac, boolean status) {
        String topic = topicTemplate.replace("+", mac);
        publisher.publish(topic, status);
    }

}
