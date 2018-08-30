package com.garden.server.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageMapper {

    private static final Logger log = LoggerFactory.getLogger(MessageMapper.class);

    private final Jackson2JsonObjectMapper objectMapper;

    public MessageMapper(Jackson2JsonObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> Optional<T> fromJson(Object json, Class<T> valueType) {
        try {
            return Optional.of(objectMapper.fromJson(json, valueType));
        } catch (Exception e) {
            log.warn("Could not deserialize message to type: [{}]", valueType, e);
        }
        return Optional.empty();
    }

    public Optional<String> toJson(Object message) {
        try {
            return Optional.of(objectMapper.toJson(message));
        } catch (Exception e) {
            log.warn("Could not serialize message: [{value}]", message, e);
        }
        return Optional.empty();
    }


}
