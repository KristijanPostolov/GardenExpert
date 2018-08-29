package com.garden.server.service;

import com.garden.server.model.HubConfiguration;
import com.garden.server.model.SensorHub;
import com.garden.server.repository.SensorHubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SensorHubService {

    private static final Logger log = LoggerFactory.getLogger(SensorHubService.class);

    private final SensorHubRepository repository;
    private final HubConfigurationService hubConfigurationService;

    public SensorHubService(SensorHubRepository repository, HubConfigurationService hubConfigurationService) {
        this.repository = repository;
        this.hubConfigurationService = hubConfigurationService;
    }

    public Optional<SensorHub> findByMac(String mac) {
        log.info("Finding sensor hub [{}]", mac);
        return repository.findByMacAddress(mac);
    }

    @Transactional
    public SensorHub getOrCreateByMac(String mac) {
        return findByMac(mac)
                .orElseGet(() -> {
                    log.info("Sensor hub [{}], does not exist. Creating new instance", mac);
                    SensorHub sensorHub = repository.save(new SensorHub(mac));
                    HubConfiguration hubConfiguration = hubConfigurationService.saveDefaultConfiguration(sensorHub);
                    sensorHub.setHubConfiguration(hubConfiguration);
                    return sensorHub;
                });
    }

    public List<SensorHub> getSensorHubsByMac(String macQuery, Integer page, Integer limit) {
        if (page != null && limit != null) {
            Pageable pageRequest = PageRequest.of(page, limit);
            log.info("Getting sensor hubs by mac query [{}] from page [{}] with limit [{}]", macQuery, page, limit);
            return repository.findByMacAddressStartingWithOrderByLastMeasurementDesc(macQuery, pageRequest);
        } else {
            log.info("Getting sensor hubs by mac query [{}]", macQuery);
            return repository.findByMacAddressStartingWithOrderByLastMeasurementDesc(macQuery);
        }
    }


    public List<SensorHub> getSensorHubsByName(String nameQuery, Integer page, Integer limit) {
        if (page != null && limit != null) {
            Pageable pageRequest = PageRequest.of(page, limit);
            log.info("Getting sensor hubs by name query [{}] from page [{}] with limit [{}]", nameQuery, page, limit);
            return repository.findByNameStartingWithOrderByLastMeasurementDesc(nameQuery, pageRequest);
        } else {
            log.info("Getting sensor hubs by name query [{}]", nameQuery);
            return repository.findByNameStartingWithOrderByLastMeasurementDesc(nameQuery);
        }
    }

    @Transactional
    public Optional<SensorHub> updateName(String mac, String name) {
        return repository.findByMacAddress(mac).map(sensorHub -> {
            sensorHub.setName(name);
            return sensorHub;
        });
    }

}
