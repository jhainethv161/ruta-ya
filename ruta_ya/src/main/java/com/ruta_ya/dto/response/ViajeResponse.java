package com.ruta_ya.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public interface ViajeResponse {
    Integer getCodigo();
    Instant getFechaHora();
    BigDecimal getValorEstimado();
    Integer getIdEstado();
    Integer getIdDireccionOrigen();
    Integer getIdDireccionDestino();
    String getCedulaUsuario();
    String getCedulaConductor();
    String getPlacaVehiculo();
}
