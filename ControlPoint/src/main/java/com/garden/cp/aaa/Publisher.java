package com.garden.cp.aaa;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

@Service
public class Publisher {

    public static final String BROKER_URL = "tcp://localhost:1883";

    public static final String TOPIC_BRIGHTNESS = "test/brightness";
    public static final String TOPIC_TEMPERATURE = "test/temperature";

    private MqttClient client;

    public Publisher() {
        //We have to generate a unique Client id.
        String clientId = "AEEEG" + "-pub";
        try {

            client = new MqttClient(BROKER_URL, clientId);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start() {

        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setWill(client.getTopic("test/LWT"), "I'm gone :(".getBytes(), 0, false);

            client.connect(options);

            //Publish data forever
            while (true) {

                publishBrightness();

                Thread.sleep(50000);

                publishTemperature();

                Thread.sleep(50000);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void publishTemperature() throws MqttException {
        final MqttTopic temperatureTopic = client.getTopic(TOPIC_TEMPERATURE);

        final int temperatureNumber = 20;
        final String temperature = temperatureNumber + "°C";

        temperatureTopic.publish(new MqttMessage(temperature.getBytes()));

        System.out.println("Published data. Topic: " + temperatureTopic.getName() + "  Message: " + temperature);
    }

    private void publishBrightness() throws MqttException {
        final MqttTopic brightnessTopic = client.getTopic(TOPIC_BRIGHTNESS);

        final int brightnessNumber = 50;
        final String brigthness = brightnessNumber + "%";

        brightnessTopic.publish(new MqttMessage(brigthness.getBytes()));

        System.out.println("Published data. Topic: " + brightnessTopic.getName() + "   Message: " + brigthness);
    }

}