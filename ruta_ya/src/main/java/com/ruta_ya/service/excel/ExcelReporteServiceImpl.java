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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExcelReporteServiceImpl {

    private final ExcelReporteService excelReporteService;
    private final ReportesService reportesService;

    // REPORTES SIMPLES

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

        Map<String, Long> porClasificacion = datos.stream()
                .collect(Collectors.groupingBy(ViajeRangoFechasReporte::getClasificacionCosto, Collectors.counting()));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List.of("Económico", "Intermedio", "Costoso").forEach(c ->
                dataset.addValue(porClasificacion.getOrDefault(c, 0L), "Viajes", c)
        );

        JFreeChart grafica = ChartFactory.createBarChart(
                "Viajes por Clasificación de Costo", "Clasificación", "Cantidad de Viajes",
                dataset, PlotOrientation.VERTICAL, false, true, false
        );

        return excelReporteService.generarExcelConGrafica("ViajesRangoFecha", columnas, datos, grafica);
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

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        for (RecaudoMetodoPagoReporte item : datos) {
            dataset.setValue(item.getNombre(), item.getRecaudo().doubleValue());
        }

        JFreeChart grafica = ChartFactory.createPieChart(
                "Recaudo por Método de Pago", dataset, true, true, false
        );

        return excelReporteService.generarExcelConGrafica("RecaudoMetodoPago", columnas, datos, grafica);
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

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ConductorViajesPromedioReporte item : datos) {
            dataset.addValue(item.getTotalViajes(), "Total Viajes", item.getCedulaConductor());
        }

        JFreeChart grafica = ChartFactory.createBarChart(
                "Conductores con más viajes que el promedio", "Conductor", "Total Viajes",
                dataset, PlotOrientation.VERTICAL, false, true, false
        );

        double promedio = datos.stream()
                .mapToLong(ConductorViajesPromedioReporte::getTotalViajes)
                .average()
                .orElse(0);

        ValueMarker marker = new ValueMarker(promedio);
        marker.setPaint(Color.RED);
        marker.setStroke(new BasicStroke(2.0f));
        marker.setLabel(String.format("Promedio: %.1f", promedio));
        marker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
        marker.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);

        CategoryPlot plot = grafica.getCategoryPlot();
        plot.addRangeMarker(marker);

        return excelReporteService.generarExcelConGrafica("ConductoresMasViajesPromedio", columnas, datos, grafica);
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
