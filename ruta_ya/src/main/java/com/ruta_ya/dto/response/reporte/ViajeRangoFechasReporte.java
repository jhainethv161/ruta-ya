package com.ruta_ya.dto.response.reporte;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ViajeRangoFechasReporte {
    Integer getCodigo();
    LocalDateTime getFechaHora();
    BigDecimal getValorEstimado();
    String getClasificacionCosto();
}
