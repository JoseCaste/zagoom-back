package com.core.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RecoveryPasswordDTO {
    private String correo;

    private int token;
}
