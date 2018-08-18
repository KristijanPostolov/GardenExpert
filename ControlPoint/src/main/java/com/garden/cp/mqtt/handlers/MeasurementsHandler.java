package com.garden.cp.mqtt.handlers;

import com.garden.cp.http.HttpUtils;
import com.garden.cp.http.Measurement;
import com.garden.cp.http.MeasurementType;
import com.garden.cp.http.MeasurementUnit;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasurementsHandler implements IMqttMessageListener {

    private static final Logger log = LoggerFactory.getLogger(MeasurementsHandler.class);
    private final HttpUtils httpUtils;

    public MeasurementsHandler(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String content = new String(message.getPayload());
        String[] parts = content.split("!");
        List<Measurement> measurements = Arrays.stream(parts)
                .skip(1)
                .map(this::parseMeasurement)
                .collect(Collectors.toList());

        String sensorHubMacAddress = parts[0];
        String endpoint = String.format("http:localhost:8080/api/hubs/%s/measurements", sensorHubMacAddress);
        log.info("Sending [{}] measurements from [{}]", measurements.size(), sensorHubMacAddress);
        for(Measurement measurement : measurements) {
            httpUtils.post(endpoint, measurement);
        }
    }

    private Measurement parseMeasurement(String measurement) {
        String[] fields = measurement.split(",");
        log.info("Parsing measurement: [{}]", measurement);
        return new Measurement(
                MeasurementType.valueOf(fields[0]),
                Float.parseFloat(fields[1]),
                MeasurementUnit.valueOf(fields[2]));
    }

}
