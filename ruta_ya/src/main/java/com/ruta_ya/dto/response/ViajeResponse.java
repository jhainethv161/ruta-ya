package com.ruta_ya.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ViajeResponse {
    Integer getCodigo();
    LocalDateTime getFechaHora();
    BigDecimal getValorEstimado();
    Integer getIdEstado();
    Integer getIdDireccionOrigen();
    Integer getIdDireccionDestino();
    String getCedulaUsuario();
    String getCedulaConductor();
    String getPlacaVehiculo();
}
