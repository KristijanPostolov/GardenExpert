package com.garden.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "hub_status")
public class HubStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensor_hub_id", nullable = false)
    @JsonIgnoreProperties("hubStatus")
    @JsonIgnore
    private SensorHub sensorHub;

    @Column(name = "heater_active", nullable = false)
    private boolean heaterActive;

    @Column(name = "sprinkler_active", nullable = false)
    private boolean sprinklerActive;

    public HubStatus() {
    }

    public HubStatus(SensorHub sensorHub, boolean heaterActive, boolean sprinklerActive) {
        this.sensorHub = sensorHub;
        this.heaterActive = heaterActive;
        this.sprinklerActive = sprinklerActive;
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

    public boolean isHeaterActive() {
        return heaterActive;
    }

    public void setHeaterActive(boolean heaterActive) {
        this.heaterActive = heaterActive;
    }

    public boolean isSprinklerActive() {
        return sprinklerActive;
    }

    public void setSprinklerActive(boolean sprinklerActive) {
        this.sprinklerActive = sprinklerActive;
    }
}
