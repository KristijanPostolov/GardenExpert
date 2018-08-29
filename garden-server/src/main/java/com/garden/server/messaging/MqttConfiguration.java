package com.garden.server.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garden.server.messaging.listeners.ConnectionListener;
import com.garden.server.messaging.listeners.MeasurementListener;
import com.garden.server.messaging.listeners.StatusUpdateListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;

import java.text.SimpleDateFormat;

@Configuration
public class MqttConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MqttConfiguration.class);

    @Bean
    public MqttClient mqttClient(@Value("${mqtt.broker-url}") String brokerUrl,
                                 @Value("${mqtt.client-id}") String clientId,
                                 @Value("${mqtt.topics.connection}") String connectionTopic,
                                 ConnectionListener connectionListener,
                                 @Value("${mqtt.topics.measurement}") String measurementTopic,
                                 MeasurementListener measurementListener,
                                 @Value("${mqtt.topics.status-update}") String statusUpdateTopic,
                                 StatusUpdateListener statusUpdateListener) throws MqttException {
        MqttClient mqttClient = new MqttClient(brokerUrl, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);

        log.info("Connecting MQTT client [{}] to broker [{}]", clientId, brokerUrl);
        mqttClient.connect(options);
        log.info("Connected successfully");

        mqttClient.subscribe(connectionTopic, connectionListener);
        mqttClient.subscribe(measurementTopic, measurementListener);
        mqttClient.subscribe(statusUpdateTopic, statusUpdateListener);
        return mqttClient;
    }

    @Bean
    public Jackson2JsonObjectMapper jsonObjectMapper() {
        return new Jackson2JsonObjectMapper();
    }

}
