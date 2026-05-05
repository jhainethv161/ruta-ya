package com.ruta_ya.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserResponse {
    private String cedula;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private LocalDate fechaNacimiento;
    private Integer idEstado;
    private Integer idMetodoPagoPref;
}
