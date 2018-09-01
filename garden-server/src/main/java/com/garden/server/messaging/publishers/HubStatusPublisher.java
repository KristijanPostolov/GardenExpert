package com.garden.server.messaging.publishers;

import com.garden.server.messaging.messages.HubStatusMessage;
import com.garden.server.model.HubStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HubStatusPublisher {

    private final MqttPublisher mqttPublisher;
    private final String topicTemplate;

    public HubStatusPublisher(MqttPublisher mqttPublisher, @Value("${mqtt.topics.status}") String topicTemplate) {
        this.mqttPublisher = mqttPublisher;
        this.topicTemplate = topicTemplate;
    }

    public void publish(HubStatus hubStatus) {
        HubStatusMessage message = new HubStatusMessage();
        message.heaterActive = hubStatus.isHeaterActive();
        message.sprinklerActive = hubStatus.isSprinklerActive();

        String mac = hubStatus.getSensorHub().getMacAddress();
        this.publish(mac, message);
    }

    public void publish(String mac, HubStatusMessage hubStatusMessage) {
        String topic = topicTemplate.replace("+", mac);
        mqttPublisher.publishRetained(topic, hubStatusMessage);
    }

}
