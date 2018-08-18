package com.garden.server.service;

import com.garden.server.model.Measurement;
import com.garden.server.model.MeasurementType;
import com.garden.server.model.SensorHub;
import com.garden.server.model.request.MeasurementRequest;
import com.garden.server.repository.MeasurementRepository;
import com.garden.server.repository.specification.MeasurementSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    private static final Logger log = LoggerFactory.getLogger(MeasurementService.class);

    private final MeasurementRepository repository;
    private final SensorHubService sensorHubService;

    public MeasurementService(MeasurementRepository repository, SensorHubService sensorHubService) {
        this.repository = repository;
        this.sensorHubService = sensorHubService;
    }

    @Transactional
    public Measurement addMeasurement(String hubMacAddress, MeasurementRequest request) {
        SensorHub sensorHub = sensorHubService.getOrCreateByMac(hubMacAddress);
        LocalDateTime now = LocalDateTime.now();
        Measurement measurement = new Measurement(request.getType(), request.getValue(), request.getUnit(),
                now, sensorHub);
        log.info("Updating last measurement time for [{}]", hubMacAddress);
        sensorHub.setLastMeasurement(now);
        log.info("Saving new measurement for sensor hub [{}]", hubMacAddress);
        return repository.save(measurement);
    }

    public Optional<List<Measurement>> getMeasurements(String hubMacAddress,
                                                       LocalDateTime from,
                                                       LocalDateTime to,
                                                       MeasurementType type) {
        return sensorHubService.findByMac(hubMacAddress)
                .map(sensorHub -> {
                    Specification<Measurement> specification = MeasurementSpecifications.byHubId(sensorHub.getId());
                    if (from != null) {
                        specification = specification.and(MeasurementSpecifications.from(from));
                    }
                    if (to != null) {
                        specification = specification.and(MeasurementSpecifications.to(to));
                    }
                    if (type != null) {
                        specification = specification.and(MeasurementSpecifications.ofType(type));
                    }
                    log.info("Finding measurements by sensor hub id [{}] and specification", sensorHub.getId(), specification);
                    return repository.findAll(specification);
                });
    }

}
