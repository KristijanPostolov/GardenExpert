package com.garden.cp.model;

import java.time.LocalDateTime;

public class MeasurementMessage {

    public MeasurementType type;
    public float value;
    public MeasurementUnit unit;
    public LocalDateTime timestamp;

}
