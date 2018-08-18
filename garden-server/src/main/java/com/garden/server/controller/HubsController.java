package com.garden.server.controller;

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
import java.util.Optional;

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
    public List<SensorHub> getSensorHubs(@RequestParam(value = "query", required = false) String query,
                                         @RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "limit", required = false) Integer limit) {
        return sensorHubService.getSensorHubs(query, page, limit);
    }

    @GetMapping("/{mac}")
    public ResponseEntity<SensorHub> getSensorHub(@PathVariable("mac") String mac) {
        return sensorHubService.findByMac(mac)
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

}
