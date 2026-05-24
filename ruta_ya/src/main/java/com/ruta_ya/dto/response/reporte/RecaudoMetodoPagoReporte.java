package com.ruta_ya.dto.response.reporte;

import java.math.BigDecimal;

public interface RecaudoMetodoPagoReporte {
    Integer getId();
    String getNombre();
    Long getCantidadPagos();
    BigDecimal getRecaudo();
}
