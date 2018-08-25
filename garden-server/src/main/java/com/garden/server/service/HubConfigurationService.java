package com.garden.server.service;

import com.garden.server.model.HubConfiguration;
import com.garden.server.model.SensorHub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:default_hub_configuration.yml")
public class HubConfigurationService {

    private final int updateIntervalInSeconds;
    private final boolean autoControl;

    public HubConfigurationService(@Value("${update-interval-in-seconds}") Integer updateIntervalInSeconds,
                                   @Value("${auto-control}") Boolean autoControl) {
        this.updateIntervalInSeconds = updateIntervalInSeconds;
        this.autoControl = autoControl;
    }

    public HubConfiguration generateDefault(SensorHub sensorHub) {
        return new HubConfiguration(sensorHub, updateIntervalInSeconds, autoControl);
    }


}
