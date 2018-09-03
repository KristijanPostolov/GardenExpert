package com.garden.cp.sevices;

import com.garden.cp.model.HubConfigurationMessage;
import com.garden.cp.model.HubStatusMessage;
import com.garden.cp.model.MeasurementMessage;
import com.garden.cp.model.MeasurementType;
import com.garden.cp.mqtt.publishers.HeaterPublisher;
import com.garden.cp.mqtt.publishers.SprinklerPublisher;
import com.garden.cp.mqtt.publishers.StatusPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MeasurementService {

    private static final Logger log = LoggerFactory.getLogger(MeasurementService.class);

    private final ConfigurationService configurationService;
    private final StatusService statusService;
    private final HeaterPublisher heaterPublisher;
    private final SprinklerPublisher sprinklerPublisher;
    private final StatusPublisher statusPublisher;

    public MeasurementService(ConfigurationService configurationService, StatusService statusService,
                              HeaterPublisher heaterPublisher, SprinklerPublisher sprinklerPublisher,
                              StatusPublisher statusPublisher) {
        this.configurationService = configurationService;
        this.statusService = statusService;
        this.heaterPublisher = heaterPublisher;
        this.sprinklerPublisher = sprinklerPublisher;
        this.statusPublisher = statusPublisher;
    }

    public void handleMeasurement(String mac, MeasurementMessage measurement) {
        HubConfigurationMessage configuration = configurationService.getConfiguration(mac);
        if(configuration == null || !configuration.autoControl) {
            return;
        }
        HubStatusMessage newStatus = new HubStatusMessage();
        boolean heaterTriggered = false;
        boolean sprinklerTriggered = false;
        if(measurement.type == MeasurementType.TEMPERATURE) {
            boolean isDay = isDay(measurement.timestamp);
            float minTemperature = isDay ? configuration.minDailyCelsius : configuration.minNightlyCelsius;
            float targetTemperature = isDay ? configuration.targetDailyCelsius : configuration.targetNightlyCelsius;

            if(measurement.value < minTemperature) {
                newStatus.heaterActive = true;
                heaterTriggered = true;
            } else if(measurement.value > targetTemperature) {
                newStatus.heaterActive = false;
                heaterTriggered = true;
            }
        } else if(measurement.type == MeasurementType.SOIL_MOISTURE) {
            if(measurement.value < configuration.minMoistureThreshold) {
                newStatus.sprinklerActive = true;
                sprinklerTriggered = true;
            }
        }
        processStatus(mac, configuration, statusService.getStatus(mac), newStatus, heaterTriggered, sprinklerTriggered);
    }

    private boolean isDay(LocalDateTime localDateTime) {
        int hourOfDay = localDateTime.getHour();
        return hourOfDay > 5 && hourOfDay < 20;
    }

    private void processStatus(String mac, HubConfigurationMessage configuration,
                               HubStatusMessage status, HubStatusMessage newStatus,
                               boolean heaterTriggered, boolean sprinklerTriggered) {
        if(heaterTriggered && newStatus.heaterActive != status.heaterActive) {
            log.info("Setting heater status on [{}] to [{}]", mac, newStatus.heaterActive);
            heaterPublisher.publishHeaterStatus(mac, newStatus.heaterActive);
        }
        if(sprinklerTriggered && newStatus.sprinklerActive != status.sprinklerActive) {
            log.info("Setting sprinkler status on [{}] to [{}]", mac, newStatus.sprinklerActive);
            sprinklerPublisher.publishSprinklerStatus(mac, true);
            startSprinklerOffTrigger(configuration.triggeredWateringDurationSeconds, mac);
        }
        status.heaterActive = newStatus.heaterActive;
        status.sprinklerActive = newStatus.sprinklerActive;
        statusPublisher.publishStatus(mac, status);
    }

    private void startSprinklerOffTrigger(int delaySeconds, String mac) {
        new Thread(() -> {
            try {
                Thread.sleep(delaySeconds * 1000);
                log.info("Setting sprinkler status on [{}] to [{}]", mac, false);
                sprinklerPublisher.publishSprinklerStatus(mac, false);
                HubStatusMessage status = statusService.getStatus(mac);
                status.sprinklerActive = false;
                statusPublisher.publishStatus(mac, status);
            } catch (InterruptedException e) {
                log.error("SprinklerOffTrigger thread failed", e);
            }
        }).start();
    }

}
