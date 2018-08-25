package com.garden.server.repository;

import com.garden.server.model.HubConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HubConfigurationRepository extends JpaRepository<HubConfiguration, Long> {

    Optional<HubConfiguration> findById(Long id);
    Optional<HubConfiguration> findBySensorHub_Id(Long sensorHubId);
    Optional<HubConfiguration> findBySensorHub_MacAddress(String macAddress);

}
