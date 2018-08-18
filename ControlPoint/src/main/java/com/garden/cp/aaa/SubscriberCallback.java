package com.garden.cp.aaa;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

@Service
public class SubscriberCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        //This is called when the connection is lost. We could reconnect here.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(topic + message.getId());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("DELIVERY COMPLETE");
    }


}