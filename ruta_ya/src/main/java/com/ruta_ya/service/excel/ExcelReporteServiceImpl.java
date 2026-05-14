package com.ruta_ya.service.excel;

import com.ruta_ya.dto.response.reporte.ConductorViajesPromedioReporte;
import com.ruta_ya.dto.response.reporte.MetodoPagoMenosUsadoReporte;
import com.ruta_ya.dto.response.reporte.PagoComisionMayorReporte;
import com.ruta_ya.dto.response.reporte.RecaudoMetodoPagoReporte;
import com.ruta_ya.dto.response.reporte.UsuarioPagoPromedioReporte;
import com.ruta_ya.dto.response.reporte.UsuarioReporte;
import com.ruta_ya.dto.response.reporte.ViajeRangoFechasReporte;
import com.ruta_ya.dto.response.reporte.ViajesConductorVehiculoReporte;
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

    // REPORTES INTERMEDIOS

    public ByteArrayInputStream obtenerViajesConductorVehiculo(LocalDate fechaInicio, LocalDate fechaFin, int cantidadViajes) throws IOException {

        List<ViajesConductorVehiculoReporte> datos = reportesService.obtenerCantidadViajesConductorVehiculoRangoFechas(fechaInicio, fechaFin, cantidadViajes);

        List<ExcelColumn<ViajesConductorVehiculoReporte>> columnas =
                List.of(
                        new ExcelColumn<>("Cedula", ViajesConductorVehiculoReporte::getCedula),
                        new ExcelColumn<>("Nombres", ViajesConductorVehiculoReporte::getNombres),
                        new ExcelColumn<>("Apellidos", ViajesConductorVehiculoReporte::getApellidos),
                        new ExcelColumn<>("Placa del vehiculo", ViajesConductorVehiculoReporte::getPlacaVehiculo),
                        new ExcelColumn<>("Numero de viajes", ViajesConductorVehiculoReporte::getNumeroViajes)
                );

        return excelReporteService.generarExcel("ViajesConductorVehiculo", columnas, datos);
    }

    public ByteArrayInputStream obtenerRecaudoMetodoPago(LocalDate fechaInicio, LocalDate fechaFin) throws IOException {

        List<RecaudoMetodoPagoReporte> datos = reportesService.obtenerRecaudoMetodoPagoRangoFechas(fechaInicio, fechaFin);

        List<ExcelColumn<RecaudoMetodoPagoReporte>> columnas =
                List.of(
                        new ExcelColumn<>("ID metodo de pago", RecaudoMetodoPagoReporte::getId),
                        new ExcelColumn<>("Nombre", RecaudoMetodoPagoReporte::getNombre),
                        new ExcelColumn<>("Cantidad de pagos", RecaudoMetodoPagoReporte::getCantidadPagos),
                        new ExcelColumn<>("Recaudo total", RecaudoMetodoPagoReporte::getRecaudo)
                );

        return excelReporteService.generarExcel("RecaudoMetodoPago", columnas, datos);
    }

    public ByteArrayInputStream obtenerUsuariosViajesMayoresValor(double valor) throws IOException {

        List<UsuarioReporte> datos = reportesService.obtenerUsuariosViajesMayoresAValor(valor);

        List<ExcelColumn<UsuarioReporte>> columnas =
                List.of(
                        new ExcelColumn<>("Cedula", UsuarioReporte::getCedula),
                        new ExcelColumn<>("Nombres", UsuarioReporte::getNombres),
                        new ExcelColumn<>("Apellidos", UsuarioReporte::getApellidos)
                );

        return excelReporteService.generarExcel("UsuariosViajesMayoresValor", columnas, datos);
    }

    // REPORTES AVANZADOS

    public ByteArrayInputStream obtenerConductoresMasViajesQuePromedio(LocalDate fechaInicio, LocalDate fechaFin) throws IOException {

        List<ConductorViajesPromedioReporte> datos = reportesService.obtenerConductoresConMasViajesQuePromedio(fechaInicio, fechaFin);

        List<ExcelColumn<ConductorViajesPromedioReporte>> columnas =
                List.of(
                        new ExcelColumn<>("Cedula del conductor", ConductorViajesPromedioReporte::getCedulaConductor),
                        new ExcelColumn<>("Placa del vehiculo", ConductorViajesPromedioReporte::getPlacaVehiculo),
                        new ExcelColumn<>("Total de viajes", ConductorViajesPromedioReporte::getTotalViajes)
                );

        return excelReporteService.generarExcel("ConductoresMasViajesPromedio", columnas, datos);
    }

    public ByteArrayInputStream obtenerUsuariosPagoMayorQuePromedio(LocalDate fechaInicio, LocalDate fechaFin) throws IOException {

        List<UsuarioPagoPromedioReporte> datos = reportesService.obtenerUsuariosConPagoMayoresQuePromedio(fechaInicio, fechaFin);

        List<ExcelColumn<UsuarioPagoPromedioReporte>> columnas =
                List.of(
                        new ExcelColumn<>("Cedula", UsuarioPagoPromedioReporte::getCedula),
                        new ExcelColumn<>("Nombres", UsuarioPagoPromedioReporte::getNombres),
                        new ExcelColumn<>("Apellidos", UsuarioPagoPromedioReporte::getApellidos),
                        new ExcelColumn<>("Total pagado", UsuarioPagoPromedioReporte::getTotalPagado)
                );

        return excelReporteService.generarExcel("UsuariosPagoMayorPromedio", columnas, datos);
    }

    public ByteArrayInputStream obtenerMetodosPagoMenosUsados(LocalDate fechaInicio, LocalDate fechaFin) throws IOException {

        List<MetodoPagoMenosUsadoReporte> datos = reportesService.obtenerMetodosPagoMenosUsados(fechaInicio, fechaFin);

        List<ExcelColumn<MetodoPagoMenosUsadoReporte>> columnas =
                List.of(
                        new ExcelColumn<>("Metodo de pago", MetodoPagoMenosUsadoReporte::getMetodoPago),
                        new ExcelColumn<>("Cantidad de usos", MetodoPagoMenosUsadoReporte::getCantidadUsos)
                );

        return excelReporteService.generarExcel("MetodosPagoMenosUsados", columnas, datos);
    }
}
