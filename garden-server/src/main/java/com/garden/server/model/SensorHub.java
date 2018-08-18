package com.garden.server.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

@Entity(name = "sensor_hub")
public class SensorHub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mac_address", unique = true)
    private String macAddress;

    @Column(name = "last_measurement")
    private LocalDateTime lastMeasurement;

    public SensorHub() {
    }

    public SensorHub(String macAddress) {
        this.macAddress = macAddress;
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
        return lastMeasurement != null && lastMeasurement.isAfter(LocalDateTime.now().minusMinutes(5));
    }
}
