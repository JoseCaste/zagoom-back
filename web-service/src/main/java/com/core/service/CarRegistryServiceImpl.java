package com.core.service;

import com.core.dto.CarInspectionDTO;
import com.core.dto.CarRegistryDTO;
import com.core.entity.CarModelEntity;
import com.core.entity.CarRegistryEntity;
import com.core.entity.Usuario;
import com.core.repository.CarRegistryRepository;
import com.core.repository.TemporalCarInspectionRepository;
import com.core.repository.UsuarioRepository;
import com.core.service.aws.S3Util;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CarRegistryServiceImpl implements CarRegistryService{

    private CarModelService carModelService;
    private UsuarioRepository usuarioRepository;
    private CarRegistryRepository carRegistryRepository;
    private TemporalCarInspectionRepository temporalCarInspectionRepository;

    public CarRegistryServiceImpl(CarModelService carModelService, UsuarioRepository usuarioRepository, CarRegistryRepository carRegistryRepository, TemporalCarInspectionRepository temporalCarInspectionRepository) {
        this.carModelService = carModelService;
        this.usuarioRepository = usuarioRepository;
        this.carRegistryRepository = carRegistryRepository;
        this.temporalCarInspectionRepository = temporalCarInspectionRepository;
    }

    @Override
    public Object saveCarRegistry(CarRegistryDTO carRegistryDTO) {
        CarModelEntity carModel = this.carModelService.findByCarModelName(carRegistryDTO.getCarModel());
        Usuario usuario = this.usuarioRepository.findById(carRegistryDTO.getIdUsuario()).orElseThrow(()-> new RuntimeException("No se ha encontrado un usuario con id: ".concat(String.valueOf(carRegistryDTO.getIdUsuario()))));

        CarRegistryEntity carRegistry = new CarRegistryEntity();
        carRegistry.setUsuario(usuario);
        carRegistry.setCarModel(carModel);
        carRegistry.setAnio(carRegistryDTO.getAnio());
        carRegistry.setColor(carRegistryDTO.getColor());
        carRegistry.setStatus(0);
        carRegistry.setNoMotor(carRegistryDTO.getNoMotor());
        this.carRegistryRepository.save(carRegistry);

        return "Registro exitoso";
    }

    @Override
    public Object saveInspection(CarInspectionDTO carInspectionDTO, MultipartFile elementInspectionFile) throws IOException {
        Usuario usuario = this.usuarioRepository.findById(carInspectionDTO.getIdUsuario()).orElseThrow(()-> new RuntimeException("No se ha encontrado un usuario con id: ".concat(String.valueOf(carInspectionDTO.getIdUsuario()))));

        S3Util.uploadFile("zagoom","/inspeccion" ,elementInspectionFile);
        return null;
    }
}
