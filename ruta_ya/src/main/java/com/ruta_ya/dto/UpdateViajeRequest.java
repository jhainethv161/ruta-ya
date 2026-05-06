package com.ruta_ya.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateViajeRequest {
    private BigDecimal valorEstimado;
    private Integer idEstado;
    private String cedulaUsuario;
    private String cedulaConductor;
    private String placaVehiculo;
    private DireccionRequest direccionOrigen;
    private DireccionRequest direccionDestino;
}
