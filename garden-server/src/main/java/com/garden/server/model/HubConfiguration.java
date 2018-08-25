package com.garden.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    public HubConfiguration() {
    }

    public HubConfiguration(SensorHub sensorHub, int updateIntervalInSeconds, boolean autoControl) {
        this.sensorHub = sensorHub;
        this.updateIntervalInSeconds = updateIntervalInSeconds;
        this.autoControl = autoControl;
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
}
