package com.core.service;

import com.core.entity.CarModelEntity;

import java.util.List;

public interface CarBrandService {

    Object getAllCars();

    List<CarModelEntity> getBrandsByCar(String brandName);
}
