package com.garden.server.messaging;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;

@Configuration
public class MqttConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MqttConfiguration.class);

    @Bean
    public MqttClient mqttClient(@Value("${mqtt.broker-url}") String brokerUrl,
                                 @Value("${mqtt.client-id}") String clientId) throws MqttException {

        MqttClient mqttClient = new MqttClient(brokerUrl, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);

        log.info("Connecting MQTT client [{}] to broker [{}]", clientId, brokerUrl);
        mqttClient.connect(options);
        log.info("Connected successfully");
        return mqttClient;
    }

    @Bean
    public Jackson2JsonObjectMapper jsonObjectMapper() {
        return new Jackson2JsonObjectMapper();
    }

}
