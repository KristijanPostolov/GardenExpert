package com.garden.cp.sevices;

import com.garden.cp.model.HubStatusMessage;
import com.garden.cp.repository.SensorHubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private static final Logger log = LoggerFactory.getLogger(StatusService.class);

    private final SensorHubRepository repository;

    public StatusService(SensorHubRepository repository) {
        this.repository = repository;
    }

    public void updateStatus(String mac, HubStatusMessage statusMessage) {
        log.info("Updating status for [{}]", mac);
        repository.putStatus(mac, statusMessage);
    }

    public HubStatusMessage getStatus(String mac) {
        return repository.getStatus(mac);
    }

}
