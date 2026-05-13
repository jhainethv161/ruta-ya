package com.ruta_ya.service.excel;

import com.ruta_ya.dto.response.reporte.PagoComisionMayorReporte;
import com.ruta_ya.dto.response.reporte.UsuarioReporte;
import com.ruta_ya.dto.response.reporte.ViajeRangoFechasReporte;
import com.ruta_ya.service.ReportesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelReporteServiceImpl {

    private final ExcelReporteService excelReporteService;
    private final ReportesService reportesService;

    public ByteArrayInputStream getUsuariosPorEstado(int estado) throws IOException {

        List<UsuarioReporte> datos = reportesService.getUsuariosPorEstado(estado);

        List<ExcelColumn<UsuarioReporte>> columnas =
                List.of(
                        new ExcelColumn<>("Cedula", UsuarioReporte::getCedula),
                        new ExcelColumn<>("Nombres", UsuarioReporte::getNombres),
                        new ExcelColumn<>("Apellidos", UsuarioReporte::getApellidos)
                );

        return excelReporteService.generarExcel("UsuariosPorEstado", columnas, datos);
    }

    public ByteArrayInputStream obtenerPagosComisionMayor(double porcentaje) throws IOException {

        List<PagoComisionMayorReporte> datos = reportesService.obtenerPagosComisionMayor(porcentaje);

        List<ExcelColumn<PagoComisionMayorReporte>> columnas =
                List.of(
                        new ExcelColumn<>("Codigo de pago", PagoComisionMayorReporte::getCodigo),
                        new ExcelColumn<>("Metodo de pago", PagoComisionMayorReporte::getIdMetodoPago),
                        new ExcelColumn<>("Comision de la plataforma", PagoComisionMayorReporte::getComisionPlataforma),
                        new ExcelColumn<>("Monto total", PagoComisionMayorReporte::getMontoTotal),
                        new ExcelColumn<>("Fecha de pago", PagoComisionMayorReporte::getFecha),
                        new ExcelColumn<>("Codigo del viaje", PagoComisionMayorReporte::getCodigoViaje),
                        new ExcelColumn<>("Codigo de la suscripcion", PagoComisionMayorReporte::getCodigoSuscripcion)
                );

        return excelReporteService.generarExcel("PagosMayorComision", columnas, datos);
    }

    public ByteArrayInputStream obtenerViajesRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) throws IOException {

        List<ViajeRangoFechasReporte> datos = reportesService.obtenerViajesRangoFechas(fechaInicio, fechaFin);

        List<ExcelColumn<ViajeRangoFechasReporte>> columnas =
                List.of(
                        new ExcelColumn<>("Codigo de viaje", ViajeRangoFechasReporte::getCodigo),
                        new ExcelColumn<>("Valor estimado", ViajeRangoFechasReporte::getValorEstimado),
                        new ExcelColumn<>("Clasificacion del costo", ViajeRangoFechasReporte::getClasificacionCosto),
                        new ExcelColumn<>("Fecha", ViajeRangoFechasReporte::getFechaHora)
                );

        return excelReporteService.generarExcel("ViajesRangoFecha", columnas, datos);
    }
}
