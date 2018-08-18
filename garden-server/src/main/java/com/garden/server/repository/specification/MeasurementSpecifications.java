package com.garden.server.repository.specification;

import com.garden.server.model.Measurement;
import com.garden.server.model.MeasurementType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class MeasurementSpecifications {

    public static Specification<Measurement> byHubId(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("sensorHub"), id);
    }

    public static Specification<Measurement> from(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), localDateTime);
    }

    public static Specification<Measurement> to(LocalDateTime localDateTime) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("timestamp"), localDateTime);
    }

    public static Specification<Measurement> ofType(MeasurementType type) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("type"), type);
    }

}
