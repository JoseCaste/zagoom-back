package com.core.service;

import com.core.bean.CarInspectionDTO;
import com.core.dto.CarRegistryDTO;
import com.core.dto.CarRegistryDetail;
import com.core.entity.CarModelEntity;
import com.core.entity.CarRegistryEntity;
import com.core.entity.ElementoAutoEntity;
import com.core.entity.TemporalCarInpectionEntity;
import com.core.entity.Usuario;
import com.core.repository.CarRegistryRepository;
import com.core.repository.ElementoAutoRepository;
import com.core.repository.TemporalCarInspectionRepository;
import com.core.repository.UsuarioRepository;
import com.core.service.aws.S3Util;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarRegistryServiceImpl implements CarRegistryService{

    private CarModelService carModelService;
    private UsuarioRepository usuarioRepository;
    private CarRegistryRepository carRegistryRepository;
    private TemporalCarInspectionRepository temporalCarInspectionRepository;
    private ElementoAutoRepository elementoAutoRepository;
    public CarRegistryServiceImpl(CarModelService carModelService, UsuarioRepository usuarioRepository, CarRegistryRepository carRegistryRepository, TemporalCarInspectionRepository temporalCarInspectionRepository, ElementoAutoRepository elementoAutoRepository) {
        this.carModelService = carModelService;
        this.usuarioRepository = usuarioRepository;
        this.carRegistryRepository = carRegistryRepository;
        this.temporalCarInspectionRepository = temporalCarInspectionRepository;
        this.elementoAutoRepository = elementoAutoRepository;
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
    public Object saveInspection(CarInspectionDTO carInspectionDTO, MultipartFile elementInspectionFile) throws Exception {
        Usuario usuario = this.usuarioRepository.findById(carInspectionDTO.getIdUsuario()).orElseThrow(()-> new RuntimeException("No se ha encontrado un usuario con id: ".concat(String.valueOf(carInspectionDTO.getIdUsuario()))));
        CarRegistryEntity carRegistry = usuario.getCarRegistries().get(usuario.getCarRegistries().size()-1);

        Optional<TemporalCarInpectionEntity> temporalCarInpectionEntity;
        TemporalCarInpectionEntity temporalCarInpectionEntityAux;
        ElementoAutoEntity elementoAuto = this.elementoAutoRepository.findByElemento(carInspectionDTO.getClaveElemento()).orElseThrow(()-> new RuntimeException("No se ha encontrado el elemento del coche especificado"));

        temporalCarInpectionEntity = this.temporalCarInspectionRepository.findByCarRegistry(carRegistry);

        if(temporalCarInpectionEntity.isPresent()) {
            temporalCarInpectionEntityAux = temporalCarInpectionEntity.get();

            if(temporalCarInpectionEntityAux.getElementoAuto().getElemento().equals(elementoAuto.getElemento())) {
                throw new Exception("El registro de coche ya tiene inspeccionado el elemento");
            }
        }

        temporalCarInpectionEntityAux = new TemporalCarInpectionEntity();
        S3Util.uploadFile("zagoom","inspeccion/"+elementInspectionFile.getOriginalFilename() ,elementInspectionFile);

        temporalCarInpectionEntityAux.setCarRegistry(carRegistry);
        temporalCarInpectionEntityAux.setElementoAuto(elementoAuto);
        temporalCarInpectionEntityAux.setBucketUri("/zagoom/inspeccion/".concat(elementInspectionFile.getOriginalFilename()));
        temporalCarInspectionRepository.save(temporalCarInpectionEntityAux);

        return "Inspección unitaria exitosa";
    }

    @Override
    public Object finalizeInspectionCar(Long idUsuario, boolean lastMovement) {
        Usuario usuario = this.usuarioRepository.findById(idUsuario).orElseThrow(()-> new RuntimeException("No se ha encontrado un usuario con id: ".concat(String.valueOf(idUsuario))));
        if(lastMovement) {
            CarRegistryEntity carRegistry = usuario.getCarRegistries().get(usuario.getCarRegistries().size()-1);
            List<TemporalCarInpectionEntity> temporalCarInpectionEntityList = carRegistry.getTemporalCarInpections();

            CarRegistryDetail carRegistryDetail = buildCarRegistryDetailObject(carRegistry, temporalCarInpectionEntityList);
            return carRegistryDetail;
        }else{
            List<CarRegistryEntity> carRegistryList = usuario.getCarRegistries();
            List<CarRegistryDetail> carRegistryDetails = new ArrayList<>();
            for (CarRegistryEntity carRegistry: carRegistryList) {
                List<TemporalCarInpectionEntity> temporalCarInpectionEntityList = carRegistry.getTemporalCarInpections();
                CarRegistryDetail carRegistryDetail = buildCarRegistryDetailObject(carRegistry,temporalCarInpectionEntityList);

                carRegistryDetails.add(carRegistryDetail);
            }

            return carRegistryDetails;
        }
    }

    private CarRegistryDetail buildCarRegistryDetailObject(CarRegistryEntity carRegistry, List<TemporalCarInpectionEntity> temporalCarInpectionEntityList) {
        CarRegistryDetail carRegistryDetail = new CarRegistryDetail();
        carRegistryDetail.setNoMotor(carRegistry.getNoMotor());
        carRegistryDetail.setCarModelName(carRegistry.getCarModel().getCarModelName());
        carRegistryDetail.setCarBrand(carRegistry.getCarModel().getCarBrand().getBrandName());
        carRegistryDetail.setColor(carRegistry.getColor());
        carRegistryDetail.setAnio(carRegistry.getAnio());

        List<CarRegistryDetail.CarInspectionRegistryDetail> carInspectionRegistryDetails =
                temporalCarInpectionEntityList.stream().map(item -> {
                    CarRegistryDetail.CarInspectionRegistryDetail carInspectionRegistryDetail = new CarRegistryDetail.CarInspectionRegistryDetail();
                    carInspectionRegistryDetail.setDescripcionElemento(item.getElementoAuto().getElemento());
                    carInspectionRegistryDetail.setBucketUri(item.getBucketUri());
                    return carInspectionRegistryDetail;
                }).collect(Collectors.toList());

        carRegistryDetail.setCarInspectionRegistryDetails(carInspectionRegistryDetails);

        return carRegistryDetail;
    }
}
