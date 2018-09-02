package com.garden.server.messaging.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class IdentifiedMessage {

    private String clientId;
    private Object content;

    public IdentifiedMessage() {

    }

    public IdentifiedMessage(String clientId, Object content) {
        this.clientId = clientId;
        this.content = content;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @JsonIgnore
    public <T> T getContent(Class<T> type) {
        return type.isInstance(content) ? type.cast(content) : null;
    }
}
