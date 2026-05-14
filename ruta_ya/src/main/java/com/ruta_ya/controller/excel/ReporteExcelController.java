package com.ruta_ya.controller.excel;

import com.ruta_ya.service.excel.ExcelReporteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping("/reportes/excel")
@RequiredArgsConstructor
public class ReporteExcelController {

    //LocalDate.of(2024,1,1), LocalDate.of(2025,12,31)
    private final ExcelReporteServiceImpl reportesService;

    // REPORTES SIMPLES

    @GetMapping("/usuarios-por-estado")
    public ResponseEntity<?> getUsuariosPorEstado(@RequestParam int estado) {
        try {
            ByteArrayInputStream reporte = reportesService.getUsuariosPorEstado(estado);
            return armarRespuestaReporte(reporte, "usuarios_por_estado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/pagos-comision-mayor")
    public ResponseEntity<?> obtenerPagosComisionMayor(@RequestParam double porcentaje) {
        try {
            ByteArrayInputStream reporte = reportesService.obtenerPagosComisionMayor(porcentaje);
            return armarRespuestaReporte(reporte, "pagos_comidion_mayor_" + porcentaje);
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
            ByteArrayInputStream reporte = reportesService.obtenerViajesRangoFechas(fechaInicio, fechaFin);
            return armarRespuestaReporte(reporte, "viajes_fechas_" + fechaInicio + "_" + fechaFin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // REPORTES INTERMEDIOS

    @GetMapping("/viajes-conductor-vehiculo")
    public ResponseEntity<?> obtenerViajesConductorVehiculo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam int cantidadViajes) {
        try {
            ByteArrayInputStream reporte = reportesService.obtenerViajesConductorVehiculo(fechaInicio, fechaFin, cantidadViajes);
            return armarRespuestaReporte(reporte, "viajes_conductor_vehiculo_" + fechaInicio + "_" + fechaFin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/recaudo-metodo-pago")
    public ResponseEntity<?> obtenerRecaudoMetodoPago(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        try {
            ByteArrayInputStream reporte = reportesService.obtenerRecaudoMetodoPago(fechaInicio, fechaFin);
            return armarRespuestaReporte(reporte, "recaudo_metodo_pago_" + fechaInicio + "_" + fechaFin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/usuarios-viajes-mayores-valor")
    public ResponseEntity<?> obtenerUsuariosViajesMayoresValor(@RequestParam double valor) {
        try {
            ByteArrayInputStream reporte = reportesService.obtenerUsuariosViajesMayoresValor(valor);
            return armarRespuestaReporte(reporte, "usuarios_viajes_mayores_valor_" + valor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private ResponseEntity<InputStreamResource> armarRespuestaReporte(ByteArrayInputStream reporte, String nombreReporte) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreReporte + ".xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(reporte));
    }
}
