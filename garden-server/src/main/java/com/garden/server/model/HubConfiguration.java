package com.garden.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "hub_configuration")
public class HubConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensor_hub_id", nullable = false)
    @JsonIgnoreProperties("hubConfiguration")
    private SensorHub sensorHub;

    @Column(name = "update_interval_in_seconds", nullable = false)
    private int updateIntervalInSeconds;

    @Column(name = "auto_control", nullable = false)
    private boolean autoControl;

    @Column(name = "min_temperature_celsius", nullable = false)
    private float minTemperatureCelsius;

    @Column(name = "max_temperature_celsius", nullable = false)
    private float maxTemperatureCelsius;

    @Column(name = "min_soil_moisture", nullable = false)
    private float minSoilMoisture;

    @Column(name = "watering_time_in_seconds", nullable = false)
    private int wateringTimeInSeconds;

    public HubConfiguration() {
    }

    public HubConfiguration(SensorHub sensorHub, int updateIntervalInSeconds, boolean autoControl,
                            float minTemperatureCelsius, float maxTemperatureCelsius,
                            float minSoilMoisture, int wateringTimeInSeconds) {
        this.sensorHub = sensorHub;
        this.updateIntervalInSeconds = updateIntervalInSeconds;
        this.autoControl = autoControl;
        this.minTemperatureCelsius = minTemperatureCelsius;
        this.maxTemperatureCelsius = maxTemperatureCelsius;
        this.minSoilMoisture = minSoilMoisture;
        this.wateringTimeInSeconds = wateringTimeInSeconds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SensorHub getSensorHub() {
        return sensorHub;
    }

    public void setSensorHub(SensorHub sensorHub) {
        this.sensorHub = sensorHub;
    }

    public int getUpdateIntervalInSeconds() {
        return updateIntervalInSeconds;
    }

    public void setUpdateIntervalInSeconds(int updateIntervalInSeconds) {
        this.updateIntervalInSeconds = updateIntervalInSeconds;
    }

    public boolean isAutoControl() {
        return autoControl;
    }

    public void setAutoControl(boolean autoControl) {
        this.autoControl = autoControl;
    }

    public float getMinTemperatureCelsius() {
        return minTemperatureCelsius;
    }

    public void setMinTemperatureCelsius(float minTemperatureCelsius) {
        this.minTemperatureCelsius = minTemperatureCelsius;
    }

    public float getMaxTemperatureCelsius() {
        return maxTemperatureCelsius;
    }

    public void setMaxTemperatureCelsius(float maxTemperatureCelsius) {
        this.maxTemperatureCelsius = maxTemperatureCelsius;
    }

    public float getMinSoilMoisture() {
        return minSoilMoisture;
    }

    public void setMinSoilMoisture(float minSoilMoisture) {
        this.minSoilMoisture = minSoilMoisture;
    }

    public int getWateringTimeInSeconds() {
        return wateringTimeInSeconds;
    }

    public void setWateringTimeInSeconds(int wateringTimeInSeconds) {
        this.wateringTimeInSeconds = wateringTimeInSeconds;
    }
}
