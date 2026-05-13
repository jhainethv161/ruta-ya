package com.ruta_ya.dto.response.reporte;

import java.math.BigDecimal;

public interface UsuarioPagoPromedioReporte {
    String getCedula();
    String getNombres();
    String getApellidos();
    BigDecimal getTotalPagado();
}
