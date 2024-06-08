package com.core.controller;

import com.core.dto.CarInspectionDTO;
import com.core.dto.CarRegistryDTO;
import com.core.service.CarRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/inspeccion-vehicular")
public class InspeccionVehicularController {

    private CarRegistryService carRegistryService;

    @Autowired
    public InspeccionVehicularController(CarRegistryService carRegistryService) {
        this.carRegistryService = carRegistryService;
    }

    @PostMapping("/guarda-registro")
    public ResponseEntity<?> saveCarRegistry(@RequestBody CarRegistryDTO carRegistryDTO) {
        return ResponseEntity.ok(this.carRegistryService.saveCarRegistry(carRegistryDTO));
    }

    @PostMapping("/guarda-inpection")
    public ResponseEntity<?> saveInspection(@RequestBody CarInspectionDTO carInspectionDTO, @RequestPart MultipartFile elementInspectionFile) throws IOException {
        return ResponseEntity.ok(this.carRegistryService.saveInspection(carInspectionDTO, elementInspectionFile));
    }
}
