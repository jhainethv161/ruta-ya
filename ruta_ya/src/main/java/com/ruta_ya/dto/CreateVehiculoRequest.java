package com.ruta_ya.dto;

import lombok.Data;

@Data
public class CreateVehiculoRequest {
    private String placa;
    private String modelo;
    private Integer idMarca;
    private Integer idTipo;
}
