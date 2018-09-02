package com.garden.cp.sevices;

import com.garden.cp.model.HubConfigurationMessage;
import com.garden.cp.repository.SensorHubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationService.class);

    private final SensorHubRepository repository;

    public ConfigurationService(SensorHubRepository repository) {
        this.repository = repository;
    }

    public void updateConfiguration(String mac, HubConfigurationMessage hubConfigurationMessage) {
        log.info("Updating configuration for [{}]", mac);
        repository.putConfiguration(mac, hubConfigurationMessage);
    }
}
