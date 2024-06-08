package com.core.service;

import com.core.entity.CarBrandEntity;
import com.core.entity.CarModelEntity;
import com.core.repository.CarBrandRepository;
import com.core.repository.CarModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarBrandServiceImpl implements CarBrandService{
    private CarBrandRepository carBrandRepository;
    private CarModelRepository carModelRepository;

    public CarBrandServiceImpl(CarBrandRepository carBrandRepository, CarModelRepository carModelRepository) {
        this.carBrandRepository = carBrandRepository;
        this.carModelRepository = carModelRepository;
    }

    @Override
    public Object getAllCars() {
        return carBrandRepository.findAll();
    }

    @Override
    public List<CarModelEntity> getBrandsByCar(String brandName) {
        CarBrandEntity carBrand = this.carBrandRepository.findByBrandName(brandName).orElseThrow(()-> new RuntimeException("No se ha encontrado un coche con el nombre de la marca: ".concat(brandName)));

        return this.carModelRepository.findByCarBrand(carBrand);
    }
}
