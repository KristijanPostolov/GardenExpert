package com.garden.cp.sevices;

import com.garden.cp.model.HubConfigurationMessage;
import com.garden.cp.model.HubStatusMessage;
import com.garden.cp.model.MeasurementMessage;
import com.garden.cp.model.MeasurementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MeasurementService {

    private static final Logger log = LoggerFactory.getLogger(MeasurementService.class);

    private final ConfigurationService configurationService;
    private final StatusService statusService;

    public MeasurementService(ConfigurationService configurationService, StatusService statusService) {
        this.configurationService = configurationService;
        this.statusService = statusService;
    }

    public void handleMeasurement(String mac, MeasurementMessage measurement) {
        HubConfigurationMessage configuration = configurationService.getConfiguration(mac);
        if(configuration == null || !configuration.autoControl) {
            return;
        }
        HubStatusMessage status = new HubStatusMessage();
        if(measurement.type == MeasurementType.TEMPERATURE) {
            boolean isDay = isDay(measurement.timestamp);
            float minTemperature = isDay ? configuration.minDailyCelsius : configuration.minNightlyCelsius;
            float targetTemperature = isDay ? configuration.targetDailyCelsius : configuration.targetNightlyCelsius;

            if(measurement.value < minTemperature) {
                status.heaterActive = true;
            } else if(measurement.value > targetTemperature) {
                status.heaterActive = false;
            }
        } else if(measurement.type == MeasurementType.SOIL_MOISTURE) {
            if(measurement.value < configuration.minMoistureThreshold) {
                status.sprinklerActive = true;
            }
        }
        // TODO: Handle status and update
    }

    private boolean isDay(LocalDateTime localDateTime) {
        int hourOfDay = localDateTime.getHour();
        return hourOfDay > 5 && hourOfDay < 20;
    }

}
