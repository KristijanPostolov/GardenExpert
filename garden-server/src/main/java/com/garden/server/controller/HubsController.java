package com.garden.server.controller;

import com.garden.server.model.HubConfiguration;
import com.garden.server.model.Measurement;
import com.garden.server.model.MeasurementType;
import com.garden.server.model.SensorHub;
import com.garden.server.model.request.MeasurementRequest;
import com.garden.server.service.MeasurementService;
import com.garden.server.service.SensorHubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/hubs")
public class HubsController {

    private static final Logger log = LoggerFactory.getLogger(HubsController.class);

    private final SensorHubService sensorHubService;
    private final MeasurementService measurementService;

    public HubsController(SensorHubService sensorHubService, MeasurementService measurementService) {
        this.sensorHubService = sensorHubService;
        this.measurementService = measurementService;
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

    @GetMapping("/{mac}")
    public ResponseEntity<SensorHub> getSensorHub(@PathVariable("mac") String mac) {
        return sensorHubService.findByMac(mac)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{mac}/name")
    public ResponseEntity<SensorHub> updateName(@PathVariable("mac") String mac, @RequestBody String name) {
        return sensorHubService.updateName(mac, name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/{mac}/measurements")
    public Measurement recordMeasurement(@PathVariable("mac") String mac, @RequestBody MeasurementRequest measurement) {
        log.info("Received [{}] measurement from [{}]", measurement.getType(), mac);
        return measurementService.addMeasurement(mac, measurement);
    }

    @GetMapping("/{mac}/measurements")
    public ResponseEntity<List<Measurement>> getMeasurements(
            @PathVariable(value = "mac", required = false) String mac,

            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,

            @RequestParam(value = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,

            @RequestParam(value = "type", required = false) MeasurementType type) {
        return measurementService.getMeasurements(mac, from, to, type)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{mac}/config")
    public HubConfiguration getHubConfiguration(@PathVariable("mac") String mac) {
        return sensorHubService.findByMac(mac)
                .map(SensorHub::getHubConfiguration)
                .orElse(null);
    }

}
