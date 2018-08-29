package com.garden.server.messaging.messages;

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

}
