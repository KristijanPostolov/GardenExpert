package com.garden.cp.mqtt.handlers;

import com.garden.cp.http.HttpUtils;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DisconnectionHandler implements IMqttMessageListener {

    private static final Logger log = LoggerFactory.getLogger(DisconnectionHandler.class);
    private final HttpUtils httpUtils;

    public DisconnectionHandler(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String sensorHubMacAddress = new String(message.getPayload());
        String endpoint = String.format("/hubs/%s", sensorHubMacAddress);
        log.info("Disconnecting sensor hub [{}]", sensorHubMacAddress);
        httpUtils.patch(endpoint, "disconnected");
    }

}
