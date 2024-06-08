package com.core.service;

import com.core.entity.CarModelEntity;
import com.core.repository.CarModelRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarModelServiceImpl implements CarModelService{
    private CarModelRepository carModelRepository;

    public CarModelServiceImpl(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @Override
    public CarModelEntity findByCarModelName(String carModelName) {
        return this.carModelRepository.findByCarModelName(carModelName).orElseThrow(() -> new RuntimeException("No se ha encontrado un modelo de coche con: ".concat(carModelName)));
    }
}
