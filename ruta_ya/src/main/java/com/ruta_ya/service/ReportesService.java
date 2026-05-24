package com.ruta_ya.service;

import com.ruta_ya.dto.response.reporte.ConductorViajesPromedioReporte;
import com.ruta_ya.dto.response.reporte.HistorialViajesConductorReporte;
import com.ruta_ya.dto.response.reporte.MetodoPagoMenosUsadoReporte;
import com.ruta_ya.dto.response.reporte.PagoComisionMayorReporte;
import com.ruta_ya.dto.response.reporte.RecaudoMetodoPagoReporte;
import com.ruta_ya.dto.response.reporte.UsuarioPagoPromedioReporte;
import com.ruta_ya.dto.response.reporte.UsuarioReporte;
import com.ruta_ya.dto.response.reporte.ViajeRangoFechasReporte;
import com.ruta_ya.dto.response.reporte.ViajesConductorVehiculoReporte;
import com.ruta_ya.repository.reporte.ReporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportesService {

    private final ReporteRepository reporteRepository;

    // REPORTES SIMPLES

    public List<UsuarioReporte> getUsuariosPorEstado(int estado) {
        try {
            return reporteRepository.getUsuariosPorEstado(estado);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los usuarios por estado");
        }
    }

    public List<PagoComisionMayorReporte> obtenerPagosComisionMayor(double porcentaje) {
        try {
            return reporteRepository.obtenerPagosComisionMayor(porcentaje);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los pagos con comisión mayor");
        }
    }

    public List<ViajeRangoFechasReporte> obtenerViajesRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            return reporteRepository.obtenerViajesRangoFechas(fechaInicio, fechaFin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los viajes en el rango de fechas");
        }
    }

    // REPORTES INTERMEDIOS

    public List<ViajesConductorVehiculoReporte> obtenerCantidadViajesConductorVehiculoRangoFechas(LocalDate fechaInicio,
                                                                                                  LocalDate fechaFin,
                                                                                                  int cantidadViajes) {
        try {
            return reporteRepository.obtenerCantidadViajesConductorVehiculoRangoFechas(fechaInicio, fechaFin, cantidadViajes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener la cantidad de viajes por conductor y vehículo");
        }
    }

    public List<RecaudoMetodoPagoReporte> obtenerRecaudoMetodoPagoRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            return reporteRepository.obtenerRecaudoMetodoPagoRangoFechas(fechaInicio, fechaFin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener el recaudo por método de pago");
        }
    }

    public List<UsuarioReporte> obtenerUsuariosViajesMayoresAValor(double valor) {
        try {
            return reporteRepository.obtenerUsuariosViajesMayoresAValor(valor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los usuarios con viajes mayores al valor");
        }
    }

    public List<HistorialViajesConductorReporte> obtenerHistorialViajesConductor(String cedula) {
        try {
            return reporteRepository.obtenerHistorialViajesConductor(cedula);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener el historial de viajes del conductor");
        }
    }

    // REPORTES AVANZADOS

    public List<ConductorViajesPromedioReporte> obtenerConductoresConMasViajesQuePromedio(LocalDate fechaInicio,
                                                                                          LocalDate fechaFin) {
        try {
            return reporteRepository.obtenerConductoresConMasViajesQuePromedio(fechaInicio, fechaFin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los conductores con más viajes que el promedio");
        }
    }

    public List<UsuarioPagoPromedioReporte> obtenerUsuariosConPagoMayoresQuePromedio(LocalDate fechaInicio,
                                                                                     LocalDate fechaFin) {
        try {
            return reporteRepository.obtenerUsuariosConPagoMayoresQuePromedio(fechaInicio, fechaFin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los usuarios con pagos mayores al promedio");
        }
    }

    public List<MetodoPagoMenosUsadoReporte> obtenerMetodosPagoMenosUsados(LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            return reporteRepository.ObtenerMetodosPagoMenosUsados(fechaInicio, fechaFin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los métodos de pago menos usados");
        }
    }

    public List<ConductorViajesPromedioReporte> obtenerCantidadViajesConductores(LocalDate fechaInicio,
                                                                                          LocalDate fechaFin) {
        try {
            return reporteRepository.obtenerCantidadViajesConductores(fechaInicio, fechaFin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los conductores con más viajes que el promedio");
        }
    }
}
