package com.garden.server.service;

import com.garden.server.model.HubConfiguration;
import com.garden.server.model.SensorHub;
import com.garden.server.model.request.HubConfigurationRequest;
import com.garden.server.repository.HubConfigurationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@PropertySource("classpath:default_hub_configuration.yml")
public class HubConfigurationService {

    // Default configuration values
    private final int defaultUpdateIntervalInSeconds;
    private final boolean defaultAutoControl;

    private final HubConfigurationRepository repository;

    public HubConfigurationService(@Value("${update-interval-in-seconds}") Integer defaultUpdateIntervalInSeconds,
                                   @Value("${auto-control}") Boolean defaultAutoControl,
                                   HubConfigurationRepository repository) {
        this.defaultUpdateIntervalInSeconds = defaultUpdateIntervalInSeconds;
        this.defaultAutoControl = defaultAutoControl;
        this.repository = repository;
    }

    public HubConfiguration generateDefault(SensorHub sensorHub) {
        return new HubConfiguration(sensorHub, defaultUpdateIntervalInSeconds, defaultAutoControl);
    }

    public Optional<HubConfiguration> getConfigurationForHubId(Long hubId) {
        return repository.findBySensorHub_Id(hubId);
    }

    public Optional<HubConfiguration> getConfigurationForMacAddress(String macAddress) {
        return repository.findBySensorHub_MacAddress(macAddress);
    }

    public Optional<HubConfiguration> updateConfigurationForMacAddress(String macAddress,
                                                                       HubConfigurationRequest request) {
        return repository.findBySensorHub_MacAddress(macAddress)
                .map(hubConfiguration -> {
                    hubConfiguration.setUpdateIntervalInSeconds(request.updateIntervalInSeconds);
                    hubConfiguration.setAutoControl(request.autoControl);
                    return hubConfiguration;
                });
    }

}
