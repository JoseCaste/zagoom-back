package com.core.repository;

import com.core.entity.CarRegistryEntity;
import com.core.entity.TemporalCarInpectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemporalCarInspectionRepository extends JpaRepository<TemporalCarInpectionEntity, Long> {
    Optional<TemporalCarInpectionEntity> findByCarRegistry(CarRegistryEntity carRegistry);
}
