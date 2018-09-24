package com.garden.server.repository;

import com.garden.server.model.HubStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HubStatusRepository extends JpaRepository<HubStatus, Long> {

    HubStatus findBySensorHub_Id(Long id);
    HubStatus findBySensorHub_MacAddress(String macAddress);

}
