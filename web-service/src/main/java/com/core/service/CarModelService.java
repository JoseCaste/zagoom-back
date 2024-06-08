package com.core.service;

import com.core.entity.CarModelEntity;

import java.util.Optional;

public interface CarModelService {

    CarModelEntity findByCarModelName(String carModelName);
}
