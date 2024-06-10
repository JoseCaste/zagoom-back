package com.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarRegistryDetail {
    private String noMotor;
    private String carModelName;
    private String carBrand;
    private String color;
    private String anio;

    List<CarInspectionRegistryDetail> carInspectionRegistryDetails;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CarInspectionRegistryDetail {
        private String descripcionElemento;
        private String bucketUri;
    }
}
