package com.garden.server.model.request;

public class HubConfigurationRequest {

    public int updateIntervalInSeconds;
    public boolean autoControl;
    public float minTemperatureCelsius;
    public float maxTemperatureCelsius;
    public float minSoilMoisture;
    public int wateringTimeInSeconds;

}
