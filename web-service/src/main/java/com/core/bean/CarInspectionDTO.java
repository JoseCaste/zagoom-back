package com.core.bean;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class CarInspectionDTO {

    private Long idUsuario;
    private String claveElemento;
}
