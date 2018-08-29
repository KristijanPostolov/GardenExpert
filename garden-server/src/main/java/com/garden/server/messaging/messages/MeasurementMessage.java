package com.garden.server.messaging.messages;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.garden.server.model.MeasurementType;
import com.garden.server.model.MeasurementUnit;

import java.time.LocalDateTime;

public class MeasurementMessage {

    public MeasurementType type;
    public float value;
    public MeasurementUnit unit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    public LocalDateTime timestamp;
}
