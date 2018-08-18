package com.garden.server.model.request;

import com.garden.server.model.MeasurementType;
import com.garden.server.model.MeasurementUnit;

public class MeasurementRequest {

    private MeasurementType type;
    private float value;
    private MeasurementUnit unit;

    public MeasurementRequest() {
    }

    public MeasurementRequest(MeasurementType type, float value, MeasurementUnit unit) {
        this.type = type;
        this.value = value;
        this.unit = unit;
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
}
