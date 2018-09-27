package com.garden.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private SensorHub sensorHub;

    @Column(name = "update_interval_seconds", nullable = false)
    private int updateIntervalSeconds;

    @Column(name = "auto_control", nullable = false)
    private boolean autoControl;

    @Column(name = "min_daily_celsius", nullable = false)
    private float minDailyCelsius;

    @Column(name = "target_daily_celsius", nullable = false)
    private float targetDailyCelsius;

    @Column(name = "min_nightly_celsius", nullable = false)
    private float minNightlyCelsius;

    @Column(name = "target_nightly_celsius", nullable = false)
    private float targetNightlyCelsius;

    @Column(name = "regular_watering_cycle_seconds", nullable = false)
    private int regularWateringCycleSeconds;

    @Column(name = "regular_watering_duration_seconds", nullable = false)
    private int regularWateringDurationSeconds;

    @Column(name = "min_moisture_threshold", nullable = false)
    private float minMoistureThreshold;

    @Column(name = "triggered_watering_duration_seconds", nullable = false)
    private int triggeredWateringDurationSeconds;

    public HubConfiguration() {
    }

    public HubConfiguration(SensorHub sensorHub, int updateIntervalSeconds, boolean autoControl,
                            float minDailyCelsius, float targetDailyCelsius,
                            float minNightlyCelsius, float targetNightlyCelsius,
                            int regularWateringCycleSeconds, int regularWateringDurationSeconds,
                            float minMoistureThreshold, int triggeredWateringDurationSeconds) {
        this.sensorHub = sensorHub;
        this.updateIntervalSeconds = updateIntervalSeconds;
        this.autoControl = autoControl;
        this.minDailyCelsius = minDailyCelsius;
        this.targetDailyCelsius = targetDailyCelsius;
        this.minNightlyCelsius = minNightlyCelsius;
        this.targetNightlyCelsius = targetNightlyCelsius;
        this.regularWateringCycleSeconds = regularWateringCycleSeconds;
        this.regularWateringDurationSeconds = regularWateringDurationSeconds;
        this.minMoistureThreshold = minMoistureThreshold;
        this.triggeredWateringDurationSeconds = triggeredWateringDurationSeconds;
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

    public int getUpdateIntervalSeconds() {
        return updateIntervalSeconds;
    }

    public void setUpdateIntervalSeconds(int updateIntervalSeconds) {
        this.updateIntervalSeconds = updateIntervalSeconds;
    }

    public boolean isAutoControl() {
        return autoControl;
    }

    public void setAutoControl(boolean autoControl) {
        this.autoControl = autoControl;
    }

    public float getMinDailyCelsius() {
        return minDailyCelsius;
    }

    public void setMinDailyCelsius(float minDailyCelsius) {
        this.minDailyCelsius = minDailyCelsius;
    }

    public float getTargetDailyCelsius() {
        return targetDailyCelsius;
    }

    public void setTargetDailyCelsius(float targetDailyCelsius) {
        this.targetDailyCelsius = targetDailyCelsius;
    }

    public float getMinNightlyCelsius() {
        return minNightlyCelsius;
    }

    public void setMinNightlyCelsius(float minNightlyCelsius) {
        this.minNightlyCelsius = minNightlyCelsius;
    }

    public float getTargetNightlyCelsius() {
        return targetNightlyCelsius;
    }

    public void setTargetNightlyCelsius(float targetNightlyCelsius) {
        this.targetNightlyCelsius = targetNightlyCelsius;
    }

    public int getRegularWateringCycleSeconds() {
        return regularWateringCycleSeconds;
    }

    public void setRegularWateringCycleSeconds(int regularWateringCycleSeconds) {
        this.regularWateringCycleSeconds = regularWateringCycleSeconds;
    }

    public int getRegularWateringDurationSeconds() {
        return regularWateringDurationSeconds;
    }

    public void setRegularWateringDurationSeconds(int regularWateringDurationSeconds) {
        this.regularWateringDurationSeconds = regularWateringDurationSeconds;
    }

    public float getMinMoistureThreshold() {
        return minMoistureThreshold;
    }

    public void setMinMoistureThreshold(float minMoistureThreshold) {
        this.minMoistureThreshold = minMoistureThreshold;
    }

    public int getTriggeredWateringDurationSeconds() {
        return triggeredWateringDurationSeconds;
    }

    public void setTriggeredWateringDurationSeconds(int triggeredWateringDurationSeconds) {
        this.triggeredWateringDurationSeconds = triggeredWateringDurationSeconds;
    }
}
