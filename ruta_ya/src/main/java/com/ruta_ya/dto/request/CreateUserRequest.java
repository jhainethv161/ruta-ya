package com.ruta_ya.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateUserRequest {
    private String cedula;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private String contrasena;
    private LocalDate fechaNacimiento;
    private Integer idEstado;
    private Integer idMetodoPagoPref;
}
