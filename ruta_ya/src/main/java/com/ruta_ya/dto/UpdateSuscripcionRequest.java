package com.ruta_ya.dto;

import lombok.Data;

@Data
public class UpdateSuscripcionRequest {
    private Integer idTipo;
    private Integer idEstado;
    private String cedulaUsuario;
}
