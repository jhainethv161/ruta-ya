package com.ruta_ya.dto.response.reporte;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PagoComisionMayorReporte {
    Integer getCodigo();
    LocalDate getFecha();
    BigDecimal getMontoTotal();
    BigDecimal getComisionPlataforma();
    Integer getIdMetodoPago();
    Integer getCodigoViaje();
    Integer getCodigoSuscripcion();
}
