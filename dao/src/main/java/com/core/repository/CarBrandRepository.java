package com.core.repository;

import com.core.entity.CarBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarBrandRepository extends JpaRepository<CarBrandEntity, Long> {

    Optional<CarBrandEntity> findByBrandName(String brandName);
}
