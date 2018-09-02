package com.garden.cp.sevices;

import com.garden.cp.model.HubStatusMessage;
import com.garden.cp.repository.SensorHubRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private final SensorHubRepository repository;

    public StatusService(SensorHubRepository repository) {
        this.repository = repository;
    }

    public void updateStatus(String mac, HubStatusMessage statusMessage) {

    }

}
