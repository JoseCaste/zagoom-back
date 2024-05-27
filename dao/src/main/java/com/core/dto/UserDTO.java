package com.core.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String correo;
    private String telefono;
    private String password;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
}
