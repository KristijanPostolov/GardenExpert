package com.garden.server.service;

import com.garden.server.model.HubConfiguration;
import com.garden.server.model.SensorHub;
import com.garden.server.messaging.messages.HubConfigurationMessage;
import com.garden.server.repository.HubConfigurationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:default_hub_configuration.yml")
public class HubConfigurationService {

    // Default configuration values
    private final int updateIntervalInSeconds;
    private final boolean autoControl;
    private final float minDailyCelsius;
    private final float targetDailyCelsius;
    private final float minNightlyCelsius;
    private final float targetNightlyCelsius;
    private final int regularWateringCycleSeconds;
    private final int regularWateringDurationSeconds;
    private final float minMoistureThreshold;
    private final int triggeredWateringDurationSeconds;

    private final HubConfigurationRepository repository;

    public HubConfigurationService(@Value("${update-interval-seconds}") Integer updateIntervalInSeconds,
                                   @Value("${auto-control}") Boolean autoControl,
                                   @Value("${min-daily-celsius}") Float minDailyCelsius,
                                   @Value("${target-daily-celsius}") Float targetDailyCelsius,
                                   @Value("${min-nightly-celsius}") Float minNightlyCelsius,
                                   @Value("${target-nightly-celsius}") Float targetNightlyCelsius,
                                   @Value("${regular-watering-cycle-seconds}") Integer regularWateringCycleSeconds,
                                   @Value("${regular-watering-duration-seconds}") Integer regularWateringDurationSeconds,
                                   @Value("${min-moisture-threshold}") Float minMoistureThreshold,
                                   @Value("${triggered-watering-duration-seconds}") Integer triggeredWateringDurationSeconds,
                                   HubConfigurationRepository repository) {
        this.updateIntervalInSeconds = updateIntervalInSeconds;
        this.autoControl = autoControl;
        this.minDailyCelsius = minDailyCelsius;
        this.targetDailyCelsius = targetDailyCelsius;
        this.minNightlyCelsius = minNightlyCelsius;
        this.targetNightlyCelsius = targetNightlyCelsius;
        this.regularWateringCycleSeconds = regularWateringCycleSeconds;
        this.regularWateringDurationSeconds = regularWateringDurationSeconds;
        this.minMoistureThreshold = minMoistureThreshold;
        this.triggeredWateringDurationSeconds = triggeredWateringDurationSeconds;
        this.repository = repository;
    }

    public HubConfiguration saveDefaultConfiguration(SensorHub sensorHub) {
        HubConfiguration hubConfiguration = new HubConfiguration(sensorHub,
                updateIntervalInSeconds, autoControl,
                minDailyCelsius, targetDailyCelsius,
                minNightlyCelsius, targetNightlyCelsius,
                regularWateringCycleSeconds, regularWateringDurationSeconds,
                minMoistureThreshold, triggeredWateringDurationSeconds);
        return repository.save(hubConfiguration);
    }

    @Transactional
    public HubConfiguration updateConfigurationForMacAddress(String macAddress, HubConfigurationMessage request) {
        HubConfiguration hubConfiguration = repository.findBySensorHub_MacAddress(macAddress);
        hubConfiguration.setUpdateIntervalSeconds(request.updateIntervalInSeconds);
        hubConfiguration.setAutoControl(request.autoControl);
        hubConfiguration.setMinDailyCelsius(request.minDailyCelsius);
        hubConfiguration.setTargetDailyCelsius(request.targetDailyCelsius);
        hubConfiguration.setMinNightlyCelsius(request.minNightlyCelsius);
        hubConfiguration.setTargetNightlyCelsius(request.targetNightlyCelsius);
        hubConfiguration.setRegularWateringCycleSeconds(request.regularWateringCycleSeconds);
        hubConfiguration.setRegularWateringDurationSeconds(request.regularWateringDurationSeconds);
        hubConfiguration.setMinMoistureThreshold(request.minMoistureThreshold);
        hubConfiguration.setTriggeredWateringDurationSeconds(request.triggeredWateringDurationSeconds);
        return hubConfiguration;
    }

}
