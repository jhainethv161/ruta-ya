package com.ruta_ya.dto;

import java.time.LocalDate;

public interface UserResponse {

    String getCedula();
    String getPrimerNombre();
    String getSegundoNombre();
    String getPrimerApellido();
    String getSegundoApellido();
    String getCorreo();
    LocalDate getFechaNacimiento();
    Integer getIdEstado();
    Integer getIdMetodoPagoPref();
}