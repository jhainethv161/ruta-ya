package com.ruta_ya.controller;

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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReportesController {

    private final ReportesService reportesService;

    // REPORTES SIMPLES

    @GetMapping("/usuarios-por-estado")
    public ResponseEntity<?> getUsuariosPorEstado(@RequestParam int estado) {
        try {
            List<UsuarioReporte> reporte = reportesService.getUsuariosPorEstado(estado);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/pagos-comision-mayor")
    public ResponseEntity<?> obtenerPagosComisionMayor(@RequestParam double porcentaje) {
        try {
            List<PagoComisionMayorReporte> reporte = reportesService.obtenerPagosComisionMayor(porcentaje);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/viajes-rango-fechas")
    public ResponseEntity<?> obtenerViajesRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        try {
            List<ViajeRangoFechasReporte> reporte = reportesService.obtenerViajesRangoFechas(fechaInicio, fechaFin);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // REPORTES INTERMEDIOS

    @GetMapping("/viajes-conductor-vehiculo")
    public ResponseEntity<?> obtenerCantidadViajesConductorVehiculoRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam int cantidadViajes) {
        try {
            List<ViajesConductorVehiculoReporte> reporte = reportesService
                    .obtenerCantidadViajesConductorVehiculoRangoFechas(fechaInicio, fechaFin, cantidadViajes);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/recaudo-metodo-pago")
    public ResponseEntity<?> obtenerRecaudoMetodoPagoRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        try {
            List<RecaudoMetodoPagoReporte> reporte = reportesService
                    .obtenerRecaudoMetodoPagoRangoFechas(fechaInicio, fechaFin);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/usuarios-viajes-mayores-valor")
    public ResponseEntity<?> obtenerUsuariosViajesMayoresAValor(@RequestParam double valor) {
        try {
            List<UsuarioReporte> reporte = reportesService.obtenerUsuariosViajesMayoresAValor(valor);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // REPORTES AVANZADOS

    @GetMapping("/conductores-mas-viajes-promedio")
    public ResponseEntity<?> obtenerConductoresConMasViajesQuePromedio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        try {
            List<ConductorViajesPromedioReporte> reporte = reportesService
                    .obtenerConductoresConMasViajesQuePromedio(fechaInicio, fechaFin);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/usuarios-pago-mayor-promedio")
    public ResponseEntity<?> obtenerUsuariosConPagoMayoresQuePromedio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        try {
            List<UsuarioPagoPromedioReporte> reporte = reportesService
                    .obtenerUsuariosConPagoMayoresQuePromedio(fechaInicio, fechaFin);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/metodos-pago-menos-usados")
    public ResponseEntity<?> obtenerMetodosPagoMenosUsados(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        try {
            List<MetodoPagoMenosUsadoReporte> reporte = reportesService
                    .obtenerMetodosPagoMenosUsados(fechaInicio, fechaFin);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
