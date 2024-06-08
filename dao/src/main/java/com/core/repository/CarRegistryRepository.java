package com.core.repository;

import com.core.entity.CarRegistryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRegistryRepository extends JpaRepository<CarRegistryEntity, Long> {
}
