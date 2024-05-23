package com.core.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
public class EmployeeDTO {

    @Valid
    @NotEmpty(message = "No se ha proprocionado un nombre")
    private String lastname;
}
