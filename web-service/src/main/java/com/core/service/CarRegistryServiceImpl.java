package com.core.service;

import com.core.dto.CarRegistryDTO;
import com.core.entity.CarModelEntity;
import com.core.entity.CarRegistryEntity;
import com.core.entity.Usuario;
import com.core.repository.CarRegistryRepository;
import com.core.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class CarRegistryServiceImpl implements CarRegistryService{

    private CarModelService carModelService;
    private UsuarioRepository usuarioRepository;
    private CarRegistryRepository carRegistryRepository;

    public CarRegistryServiceImpl(CarModelService carModelService, UsuarioRepository usuarioRepository, CarRegistryRepository carRegistryRepository) {
        this.carModelService = carModelService;
        this.usuarioRepository = usuarioRepository;
        this.carRegistryRepository = carRegistryRepository;
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
}
