package com.garden.cp.repository;

import com.garden.cp.model.HubConfigurationMessage;
import com.garden.cp.model.HubStatusMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SensorHubRepository {

    private final Map<String, HubConfigurationMessage> configurations = new HashMap<>();
    private final Map<String, HubStatusMessage> statuses = new HashMap<>();

    public void putConfiguration(String mac, HubConfigurationMessage configuration) {
        configurations.put(mac, configuration);
    }

    public void putStatus(String mac, HubStatusMessage status) {
        statuses.put(mac, status);
    }

    public HubConfigurationMessage getConfiguration(String mac) {
        return configurations.get(mac);
    }

    public HubStatusMessage getStatus(String mac) {
        return statuses.get(mac);
    }

    public boolean hasConfiguration(String mac) {
        return configurations.containsKey(mac);
    }

    public boolean hasStatus(String mac) {
        return statuses.containsKey(mac);
    }


}
