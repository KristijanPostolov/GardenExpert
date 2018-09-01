package com.garden.server.messaging.publishers;

import com.garden.server.messaging.messages.HubConfigurationMessage;
import com.garden.server.model.HubConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HubConfigurationPublisher {

    private final MqttPublisher mqttPublisher;
    private final String topicTemplate;

    public HubConfigurationPublisher(MqttPublisher mqttPublisher,
                                     @Value("${mqtt.topics.configuration}") String topicTemplate) {
        this.mqttPublisher = mqttPublisher;
        this.topicTemplate = topicTemplate;
    }

    public void publish(HubConfiguration hubConfiguration) {
        HubConfigurationMessage message = new HubConfigurationMessage();
        message.updateIntervalInSeconds = hubConfiguration.getUpdateIntervalSeconds();
        message.autoControl = hubConfiguration.isAutoControl();
        message.minDailyCelsius = hubConfiguration.getMinDailyCelsius();
        message.targetDailyCelsius = hubConfiguration.getTargetDailyCelsius();
        message.minNightlyCelsius = hubConfiguration.getMinNightlyCelsius();
        message.targetNightlyCelsius = hubConfiguration.getTargetNightlyCelsius();
        message.regularWateringCycleSeconds = hubConfiguration.getRegularWateringCycleSeconds();
        message.regularWateringDurationSeconds = hubConfiguration.getRegularWateringDurationSeconds();
        message.minMoistureThreshold = hubConfiguration.getMinMoistureThreshold();
        message.triggeredWateringDurationSeconds = hubConfiguration.getTriggeredWateringDurationSeconds();

        String mac = hubConfiguration.getSensorHub().getMacAddress();
        this.publish(mac, message);
    }

    public void publish(String mac, HubConfigurationMessage message) {
        String topic = topicTemplate.replace("+", mac);
        mqttPublisher.publishRetained(topic, message);
    }

}
