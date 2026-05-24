package com.ruta_ya.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String contrasena;
}
