package com.ruta_ya.dto;

import lombok.Data;

@Data
public class UpdateVehiculoRequest {
    private String modelo;
    private Integer idMarca;
    private Integer idTipo;
}
