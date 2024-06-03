package com.core.repository;

import com.core.entity.CarBrandEntity;
import com.core.entity.CarModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarModelRepository extends JpaRepository<CarModelEntity, Long> {
    List<CarModelEntity> findByCarBrand(CarBrandEntity carBrand);
}
