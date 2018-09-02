package com.garden.server.messaging.messages;

import com.garden.server.model.HubStatus;

public class HubStatusMessage {

    public boolean heaterActive;
    public boolean sprinklerActive;

    public boolean isSame(HubStatus hubStatus) {
        return hubStatus != null &&
                heaterActive == hubStatus.isHeaterActive() &&
                sprinklerActive == hubStatus.isSprinklerActive();
    }

}
