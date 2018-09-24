package com.garden.server.repository;

import com.garden.server.model.HubConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HubConfigurationRepository extends JpaRepository<HubConfiguration, Long> {

    HubConfiguration findBySensorHub_Id(Long id);
    HubConfiguration findBySensorHub_MacAddress(String macAddress);

}
