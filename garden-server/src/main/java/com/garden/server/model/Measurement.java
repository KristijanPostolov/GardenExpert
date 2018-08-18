package com.garden.server.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MeasurementType type;

    private float value;

    @Enumerated(EnumType.STRING)
    private MeasurementUnit unit;

    private LocalDateTime timestamp;

    @JoinColumn(name = "sensor_hub")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SensorHub sensorHub;

    public Measurement() {
    }

    public Measurement(MeasurementType type, float value, MeasurementUnit unit,
                       LocalDateTime timestamp, SensorHub sensorHub) {
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
        this.sensorHub = sensorHub;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeasurementType getType() {
        return type;
    }

    public void setType(MeasurementType type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public MeasurementUnit getUnit() {
        return unit;
    }

    public void setUnit(MeasurementUnit unit) {
        this.unit = unit;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public SensorHub getSensorHub() {
        return sensorHub;
    }

    public void setSensorHub(SensorHub sensorHub) {
        this.sensorHub = sensorHub;
    }
}
