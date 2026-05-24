package com.ruta_ya.dto.response.reporte;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface HistorialViajesConductorReporte {
    Integer getCodigo();
    LocalDateTime getFechaHora();
    BigDecimal getValorEstimado();
    String getEstadoViaje();
    String getDireccionOrigen();
    String getDireccionDestino();
    String getCedulaUsuario();
    String getNombreUsuario();
    String getPlacaVehiculo();
    BigDecimal getMontoTotal();
}
