package com.core.controller;

import com.core.entity.CarModelEntity;
import com.core.service.CarBrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

    private CarBrandService carBrandService;

    public CatalogoController(CarBrandService carBrandService) {
        this.carBrandService = carBrandService;
    }

    @GetMapping("/coches")
    public ResponseEntity<?> getAllCars() {
        return ResponseEntity.ok(this.carBrandService.getAllCars());
    }

    @GetMapping("/coches/{brandName}")
    public ResponseEntity<List<CarModelEntity>> getBrandByCar(@PathVariable(name = "brandName") String brandName){
        return ResponseEntity.ok(this.carBrandService.getBrandsByCar(brandName));
    }
}
