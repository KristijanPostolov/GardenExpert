package com.garden.server.controller;

import com.garden.server.messaging.messages.HubConfigurationMessage;
import com.garden.server.messaging.messages.HubStatusMessage;
import com.garden.server.model.*;
import com.garden.server.service.HubConfigurationService;
import com.garden.server.service.HubStatusService;
import com.garden.server.service.MeasurementService;
import com.garden.server.service.SensorHubService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/hubs")
public class HubsController {

    private final SensorHubService sensorHubService;
    private final MeasurementService measurementService;
    private final HubStatusService hubStatusService;
    private final HubConfigurationService hubConfigurationService;

    public HubsController(SensorHubService sensorHubService, MeasurementService measurementService,
                          HubStatusService hubStatusService, HubConfigurationService hubConfigurationService) {
        this.sensorHubService = sensorHubService;
        this.measurementService = measurementService;
        this.hubStatusService = hubStatusService;
        this.hubConfigurationService = hubConfigurationService;
    }

    @GetMapping
    public ResponseEntity<List<SensorHub>> getSensorHubs(
            @RequestParam(value = "mac", required = false) String macQuery,
            @RequestParam(value = "name", required = false) String nameQuery,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {

        if (macQuery != null && nameQuery == null) {
            return ResponseEntity.ok(sensorHubService.getSensorHubsByMac(macQuery, page, limit));
        } else if (macQuery == null && nameQuery != null) {
            return ResponseEntity.ok(sensorHubService.getSensorHubsByName(nameQuery, page, limit));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorHub> getSensorHub(@PathVariable("id") Long id) {
        return sensorHubService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<SensorHub> updateName(@PathVariable("id") Long id, @RequestBody String name) {
        return sensorHubService.updateName(id, name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/measurements")
    public ResponseEntity<List<Measurement>> getMeasurements(
            @PathVariable(value = "id") Long id,

            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,

            @RequestParam(value = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,

            @RequestParam(value = "type", required = false) MeasurementType type) {

        return measurementService.getMeasurements(id, from, to, type)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public HubStatus updateStatus(@PathVariable("id") Long id, @RequestBody HubStatusMessage hubStatusMessage) {
        return hubStatusService.update(id, hubStatusMessage);
    }

    @PatchMapping("/{id}/configuration")
    public HubConfiguration updateConfiguration(@PathVariable("id") Long id,
                                                @RequestBody HubConfigurationMessage hubConfigurationMessage) {
        return hubConfigurationService.update(id, hubConfigurationMessage);
    }

}
