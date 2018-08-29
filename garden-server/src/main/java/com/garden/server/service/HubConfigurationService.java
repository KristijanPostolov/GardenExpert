package com.garden.server.service;

import com.garden.server.model.HubConfiguration;
import com.garden.server.model.SensorHub;
import com.garden.server.model.request.HubConfigurationRequest;
import com.garden.server.repository.HubConfigurationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@PropertySource("classpath:default_hub_configuration.yml")
public class HubConfigurationService {

    // Default configuration values
    private final int defaultUpdateIntervalInSeconds;
    private final boolean defaultAutoControl;
    private final float defaultMinTemperatureCelsius;
    private final float defaultMaxTemperatureCelsius;
    private final float defaultMinSoilMoisture;
    private final int defaultWateringTimeInSeconds;

    private final HubConfigurationRepository repository;

    public HubConfigurationService(@Value("${update-interval-in-seconds}") Integer defaultUpdateIntervalInSeconds,
                                   @Value("${auto-control}") Boolean defaultAutoControl,
                                   @Value("${min-temperature-celsius}") Float defaultMinTemperatureCelsius,
                                   @Value("${max-temperature-celsius}") Float defaultMaxTemperatureCelsius,
                                   @Value("${min-soil-moisture}") Float defaultMinSoilMoisture,
                                   @Value("${watering-time-in-seconds}") Integer defaultWateringTimeInSeconds,
                                   HubConfigurationRepository repository) {
        this.defaultUpdateIntervalInSeconds = defaultUpdateIntervalInSeconds;
        this.defaultAutoControl = defaultAutoControl;
        this.defaultMinTemperatureCelsius = defaultMinTemperatureCelsius;
        this.defaultMaxTemperatureCelsius = defaultMaxTemperatureCelsius;
        this.defaultMinSoilMoisture = defaultMinSoilMoisture;
        this.defaultWateringTimeInSeconds = defaultWateringTimeInSeconds;
        this.repository = repository;
    }

    public HubConfiguration saveDefaultConfiguration(SensorHub sensorHub) {
        HubConfiguration hubConfiguration = new HubConfiguration(sensorHub,
                defaultUpdateIntervalInSeconds, defaultAutoControl,
                defaultMinTemperatureCelsius, defaultMaxTemperatureCelsius,
                defaultMinSoilMoisture, defaultWateringTimeInSeconds);
        return repository.save(hubConfiguration);

    }

    public Optional<HubConfiguration> getConfigurationForMacAddress(String macAddress) {
        return repository.findBySensorHub_MacAddress(macAddress);
    }

    @Transactional
    public Optional<HubConfiguration> updateConfigurationForMacAddress(String macAddress,
                                                                       HubConfigurationRequest request) {
        return repository.findBySensorHub_MacAddress(macAddress)
                .map(hubConfiguration -> {
                    hubConfiguration.setUpdateIntervalInSeconds(request.updateIntervalInSeconds);
                    hubConfiguration.setAutoControl(request.autoControl);
                    hubConfiguration.setMinTemperatureCelsius(request.minTemperatureCelsius);
                    hubConfiguration.setMaxTemperatureCelsius(request.maxTemperatureCelsius);
                    hubConfiguration.setMinSoilMoisture(request.minSoilMoisture);
                    hubConfiguration.setWateringTimeInSeconds(request.wateringTimeInSeconds);
                    return hubConfiguration;
                });
    }

}
