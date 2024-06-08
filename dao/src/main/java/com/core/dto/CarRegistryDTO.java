package com.core.dto;

import lombok.Data;

@Data
public class CarRegistryDTO {

    private Long idUsuario;
    private String carModel;
    private String anio;
    private String color;
    private String noMotor;
}
