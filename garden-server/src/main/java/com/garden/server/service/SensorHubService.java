package com.garden.server.service;

import com.garden.server.model.SensorHub;
import com.garden.server.repository.SensorHubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorHubService {

    private static final Logger log = LoggerFactory.getLogger(SensorHubService.class);

    private final SensorHubRepository repository;

    public SensorHubService(SensorHubRepository repository) {
        this.repository = repository;
    }

    public Optional<SensorHub> findByMac(String mac) {
        log.info("Finding sensor hub [{}]", mac);
        return repository.findByMacAddress(mac);
    }

    public SensorHub getOrCreateByMac(String mac) {
        return findByMac(mac)
                .orElseGet(() -> {
                    log.info("Sensor hub [{}], does not exist. Creating new instance", mac);
                    return repository.save(new SensorHub(mac));
                });

    }

    public List<SensorHub> getSensorHubs(String macQuery, Integer page, Integer limit) {
        if(page != null && limit != null) {
            return getSensorHubsByQueryAndPage(macQuery, page, limit);
        } else {
            return getSensorHubsByQuery(macQuery);
        }
    }

    private List<SensorHub> getSensorHubsByQuery(String macQuery) {
        log.info("Getting sensor hubs by query [{}]", macQuery);
        return repository.findByMacAddressStartingWithOrderByLastMeasurementDesc(macQuery);
    }

    private List<SensorHub> getSensorHubsByQueryAndPage(String macQuery, int page, int limit) {
        Pageable pageRequest = PageRequest.of(page, limit);
        log.info("Getting sensor hubs by query [{}] from page [{}] with limit [{}]", macQuery, page, limit);
        return repository.findByMacAddressStartingWithOrderByLastMeasurementDesc(macQuery, pageRequest);
    }

}
