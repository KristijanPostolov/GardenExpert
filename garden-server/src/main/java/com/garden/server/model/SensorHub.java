package com.garden.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_hub")
public class SensorHub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "mac_address", unique = true)
    private String macAddress;

    @Column(name = "last_measurement")
    private LocalDateTime lastMeasurement;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "sensorHub")
    @JsonIgnoreProperties("sensorHub")
    private HubConfiguration hubConfiguration;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "sensorHub")
    @JsonIgnoreProperties("sensorHub")
    private HubStatus hubStatus;

    public SensorHub() {
    }

    public SensorHub(String macAddress) {
        this.macAddress = macAddress;
    }

    public SensorHub(String macAddress, String name) {
        this.macAddress = macAddress;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public LocalDateTime getLastMeasurement() {
        return lastMeasurement;
    }

    public void setLastMeasurement(LocalDateTime lastMeasurement) {
        this.lastMeasurement = lastMeasurement;
    }

    public boolean getIsConnected() {
        int updateIntervalInSeconds = hubConfiguration.getUpdateIntervalSeconds();
        return lastMeasurement != null &&
                lastMeasurement.isAfter(LocalDateTime.now().minusSeconds(updateIntervalInSeconds));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HubConfiguration getHubConfiguration() {
        return hubConfiguration;
    }

    public void setHubConfiguration(HubConfiguration hubConfiguration) {
        this.hubConfiguration = hubConfiguration;
    }

    public HubStatus getHubStatus() {
        return hubStatus;
    }

    public void setHubStatus(HubStatus hubStatus) {
        this.hubStatus = hubStatus;
    }
}
