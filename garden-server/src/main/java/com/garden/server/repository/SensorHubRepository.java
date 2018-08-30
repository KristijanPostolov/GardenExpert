package com.garden.server.repository;

import com.garden.server.model.SensorHub;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorHubRepository extends JpaRepository<SensorHub, Long> {

    Optional<SensorHub> findById(Long id);
    Optional<SensorHub> findByMacAddress(String macAddress);

    //TODO: ignore case
    List<SensorHub> findByMacAddressStartingWithOrderByLastMeasurementDesc(String mac);
    List<SensorHub> findByMacAddressStartingWithOrderByLastMeasurementDesc(String mac, Pageable pageable);

    List<SensorHub> findByNameStartingWithOrderByLastMeasurementDesc(String name);
    List<SensorHub> findByNameStartingWithOrderByLastMeasurementDesc(String name, Pageable pageable);

}
