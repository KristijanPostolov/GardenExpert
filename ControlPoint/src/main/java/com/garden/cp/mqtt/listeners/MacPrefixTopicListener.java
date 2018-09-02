package com.garden.cp.mqtt.listeners;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MacPrefixTopicListener implements IMqttMessageListener {

    private final Logger log = LoggerFactory.getLogger(MacPrefixTopicListener.class);

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        log.debug("Received MQTT message on [{}]", topic);
        String macAddress = topic.replaceAll("/.*", "");
        handleMessage(macAddress, message);
    }

    public abstract void handleMessage(String mac, MqttMessage message);
}
