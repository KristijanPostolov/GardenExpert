package com.garden.server.repository;

import com.garden.server.model.SensorHub;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorHubRepository extends JpaRepository<SensorHub, Long> {

    Optional<SensorHub> findByMacAddress(String macAddress);
    List<SensorHub> findByMacAddressStartingWithOrderByLastMeasurementDesc(String mac);
    List<SensorHub> findByMacAddressStartingWithOrderByLastMeasurementDesc(String mac, Pageable pageable);

}
