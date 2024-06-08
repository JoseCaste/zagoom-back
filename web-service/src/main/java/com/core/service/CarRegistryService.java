package com.core.service;

import com.core.dto.CarInspectionDTO;
import com.core.dto.CarRegistryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CarRegistryService {
    Object saveCarRegistry(CarRegistryDTO carRegistryDTO);

    Object saveInspection(CarInspectionDTO carInspectionDTO, MultipartFile elementInspectionFile) throws IOException;
}
