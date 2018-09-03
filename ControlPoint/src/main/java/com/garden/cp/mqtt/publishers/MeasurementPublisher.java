package com.garden.cp.mqtt.publishers;

import com.garden.cp.model.MeasurementMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MeasurementPublisher {

    private final MqttPublisher publisher;
    private final String topicTemplate;

    public MeasurementPublisher(MqttPublisher publisher,
                                @Value("${remote.topics.measurement}") String topicTemplate) {
        this.publisher = publisher;
        this.topicTemplate = topicTemplate;
    }

    public void publishMeasurement(String mac, MeasurementMessage measurementMessage) {
        String topic = topicTemplate.replace("+", mac);
        publisher.publish(topic, measurementMessage, false);
    }
}
