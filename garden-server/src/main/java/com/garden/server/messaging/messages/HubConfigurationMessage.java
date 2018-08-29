package com.garden.server.messaging.messages;

import com.garden.server.model.HubConfiguration;

public class HubConfigurationMessage {

    public int updateIntervalInSeconds;
    public boolean autoControl;
    public float minDailyCelsius;
    public float targetDailyCelsius;
    public float minNightlyCelsius;
    public float targetNightlyCelsius;
    public int regularWateringCycleSeconds;
    public int regularWateringDurationSeconds;
    public float minMoistureThreshold;
    public int triggeredWateringDurationSeconds;

    public boolean isSame(HubConfiguration hubConfiguration) {
        return hubConfiguration != null &&
                updateIntervalInSeconds == hubConfiguration.getUpdateIntervalSeconds() &&
                autoControl == hubConfiguration.isAutoControl() &&
                minDailyCelsius == hubConfiguration.getMinDailyCelsius() &&
                targetDailyCelsius == hubConfiguration.getTargetDailyCelsius() &&
                minNightlyCelsius == hubConfiguration.getMinNightlyCelsius() &&
                targetNightlyCelsius == hubConfiguration.getTargetNightlyCelsius() &&
                regularWateringCycleSeconds == hubConfiguration.getRegularWateringCycleSeconds() &&
                regularWateringDurationSeconds == hubConfiguration.getRegularWateringDurationSeconds() &&
                minMoistureThreshold == hubConfiguration.getMinMoistureThreshold() &&
                triggeredWateringDurationSeconds == hubConfiguration.getTriggeredWateringDurationSeconds();
    }

}
