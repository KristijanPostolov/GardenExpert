package com.garden.server.model.request;

import com.garden.server.model.MeasurementType;
import com.garden.server.model.MeasurementUnit;

public class MeasurementRequest {

    public MeasurementType type;
    public float value;
    public MeasurementUnit unit;


    @Override
    public String toString() {
        return String.format("%s was %f %s", type, value, unit);
    }
}
