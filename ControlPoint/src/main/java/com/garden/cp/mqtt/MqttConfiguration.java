package com.garden.cp.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MqttConfiguration.class);

    @Bean
    public MqttClient localMqttClient(@Value("${local.broker-url}") String brokerUrl,
                                      @Value("${local.client-id}") String clientId) throws MqttException {
        log.info("Connecting local MQTT client [{}], to broker [{}]", clientId, brokerUrl);
        MqttClient client = startNewMqttClient(brokerUrl, clientId);
        log.info("Local MQTT client [{}] connected", clientId);
        return client;
    }

    @Bean
    public MqttClient remoteMqttClient(@Value("${remote.broker-url}") String brokerUrl) throws MqttException {
        String clientId = MqttClient.generateClientId();
        log.info("Connecting web MQTT client [{}], to broker [{}]", clientId, brokerUrl);
        MqttClient client = startNewMqttClient(brokerUrl, clientId);
        log.info("Web MQTT client [{}] connected", clientId);
        return client;
    }

    private MqttClient startNewMqttClient(String brokerUrl, String clientId) throws MqttException {
        MqttClient mqttClient = new MqttClient(brokerUrl, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        mqttClient.connect(options);
        return mqttClient;
    }

    @Bean
    public MqttPersistentSubscriber localSubscriber(MqttClient localMqttClient) {
        return new MqttPersistentSubscriber(localMqttClient);
    }

    @Bean
    public MqttPersistentSubscriber remoteSubscriber(MqttClient remoteMqttClient) {
        return new MqttPersistentSubscriber(remoteMqttClient);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
