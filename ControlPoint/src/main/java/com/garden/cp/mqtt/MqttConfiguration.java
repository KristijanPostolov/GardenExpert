package com.garden.cp.mqtt;

import com.garden.cp.mqtt.handlers.ConnectionHandler;
import com.garden.cp.mqtt.handlers.DisconnectionHandler;
import com.garden.cp.mqtt.handlers.MeasurementsHandler;
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
    public MqttClient mqttClient(@Value("${broker_url}") String brokerUrl,
                                 @Value("${client_id}") String clientId,
                                 ConnectionHandler connectionHandler,
                                 MeasurementsHandler measurementsHandler,
                                 DisconnectionHandler disconnectionHandler) throws MqttException {

        MqttClient mqttClient = new MqttClient(brokerUrl, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);

        log.info("Connecting MQTT client [{}], to broker [{}]", clientId, brokerUrl);
        mqttClient.connect(options);
        log.info("MQTT client [{}] connected", clientId);

        mqttClient.subscribe("connected", connectionHandler);
        mqttClient.subscribe("measurements", measurementsHandler);
        mqttClient.subscribe("disconnected", disconnectionHandler);
        return mqttClient;
    }

}
