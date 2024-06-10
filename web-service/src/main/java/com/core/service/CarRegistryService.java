package com.core.service;

import com.core.bean.CarInspectionDTO;
import com.core.dto.CarRegistryDTO;
import org.springframework.web.multipart.MultipartFile;

public interface CarRegistryService {
    Object saveCarRegistry(CarRegistryDTO carRegistryDTO);

    Object saveInspection(CarInspectionDTO carInspectionDTO, MultipartFile elementInspectionFile) throws Exception;

    Object finalizeInspectionCar(Long idUsuario, boolean lastMovement);
}
