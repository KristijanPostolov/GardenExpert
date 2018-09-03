package com.garden.cp.mqtt.publishers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HeaterPublisher {

    private final MqttTriggerPublisher publisher;
    private final String topicTemplate;

    public HeaterPublisher(MqttTriggerPublisher publisher, @Value("${local.topics.heater}") String topicTemplate) {
        this.publisher = publisher;
        this.topicTemplate = topicTemplate;
    }

    public void publishHeaterStatus(String mac, boolean status) {
        String topic = topicTemplate.replace("+", mac);
        publisher.publish(topic, status);
    }
}
