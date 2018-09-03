package com.garden.server.messaging.messages;

import com.garden.server.model.MeasurementType;
import com.garden.server.model.MeasurementUnit;

import java.time.LocalDateTime;

public class MeasurementMessage {

    public MeasurementType type;
    public float value;
    public MeasurementUnit unit;
    public LocalDateTime timestamp;

}
