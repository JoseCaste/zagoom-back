package com.core.controller;

import com.core.bean.CarInspectionDTO;
import com.core.dto.CarRegistryDTO;
import com.core.service.CarRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

    @PostMapping(value = "/guarda-inspeccion", consumes = {"multipart/form-data" })
    public ResponseEntity<?> saveInspection(@RequestParam @Valid Long idUsuario, @RequestParam @Valid String claveElemento, @RequestParam @RequestPart @Valid MultipartFile elementInspectionFile) throws Exception {
        CarInspectionDTO carInspectionDTO = CarInspectionDTO.builder()
                .idUsuario(idUsuario)
                .claveElemento(claveElemento)
                .build();
        return ResponseEntity.ok(this.carRegistryService.saveInspection(carInspectionDTO, elementInspectionFile));
    }

    @PostMapping(value = "/consulta-registro-vehicular")
    public ResponseEntity<?> getCarRegistry(@RequestParam("idUsuario") @Valid Long idUsuario, @RequestParam(value = "last", defaultValue = "false") boolean lastMovement) {
        return ResponseEntity.ok(this.carRegistryService.finalizeInspectionCar(idUsuario, lastMovement));
    }
}
