package com.ruta_ya.service.excel;

import com.ruta_ya.dto.response.reporte.ConductorViajesPromedioReporte;
import com.ruta_ya.dto.response.reporte.HistorialViajesConductorReporte;
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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExcelReporteServiceImpl {

    private final ExcelReporteService excelReporteService;
    private final ReportesService reportesService;

    // Paleta de colores
    private static final Color COLOR_AMBAR = Color.decode("#f59e0b");
    private static final Color COLOR_ROJO = Color.decode("#ff5757");
    private static final Color COLOR_GRIS = Color.decode("#6b7280");
    private static final Color COLOR_OSCURO = Color.decode("#1f2937");
    private static final Color COLOR_GRID = new Color(0xE5, 0xE7, 0xEB);
    private static final Color[] PALETA = { COLOR_AMBAR, COLOR_ROJO, COLOR_GRIS, COLOR_OSCURO };

    // Tipografía
    private static final Font FUENTE_TITULO = new Font("SansSerif", Font.BOLD, 16);
    private static final Font FUENTE_EJE = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font FUENTE_ETIQUETA = new Font("SansSerif", Font.PLAIN, 11);
    private static final Font FUENTE_MARCADOR = new Font("SansSerif", Font.BOLD, 11);

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

        List<String> orden = List.of("Económico", "Intermedio", "Costoso");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        orden.forEach(c -> dataset.addValue(porClasificacion.getOrDefault(c, 0L), "Viajes", c));

        JFreeChart grafica = ChartFactory.createBarChart(
                "Viajes por Clasificación de Costo", "Clasificación", "Cantidad de Viajes",
                dataset, PlotOrientation.VERTICAL, false, true, false
        );

        // Cada barra con un color semántico: gris (económico), ámbar (intermedio), rojo (costoso)
        final Color[] coloresClasificacion = { COLOR_GRIS, COLOR_AMBAR, COLOR_ROJO };
        BarRenderer renderer = new BarRenderer() {
            @Override
            public Paint getItemPaint(int row, int column) {
                return coloresClasificacion[column % coloresClasificacion.length];
            }
        };
        estilizarGraficaCategoria(grafica, renderer);

        return excelReporteService.generarExcelConGrafica(
                "ViajesRangoFecha", columnas, datos, grafica, 750, 500
        );
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

        estilizarGraficaPie(grafica, dataset);

        // Tamaño proporcional al número de métodos (mín 700, +60 px por cada uno extra de 3)
        int ancho = Math.max(700, 700 + Math.max(0, datos.size() - 3) * 60);
        return excelReporteService.generarExcelConGrafica(
                "RecaudoMetodoPago", columnas, datos, grafica, ancho, 520
        );
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

    public ByteArrayInputStream obtenerHistorialViajesConductor(String cedula) throws IOException {

        List<HistorialViajesConductorReporte> datos = reportesService.obtenerHistorialViajesConductor(cedula);

        List<ExcelColumn<HistorialViajesConductorReporte>> columnas =
                List.of(
                        new ExcelColumn<>("Codigo del viaje", HistorialViajesConductorReporte::getCodigo),
                        new ExcelColumn<>("Fecha y hora", HistorialViajesConductorReporte::getFechaHora),
                        new ExcelColumn<>("Valor estimado", HistorialViajesConductorReporte::getValorEstimado),
                        new ExcelColumn<>("Estado del viaje", HistorialViajesConductorReporte::getEstadoViaje),
                        new ExcelColumn<>("Direccion de origen", HistorialViajesConductorReporte::getDireccionOrigen),
                        new ExcelColumn<>("Direccion de destino", HistorialViajesConductorReporte::getDireccionDestino),
                        new ExcelColumn<>("Cedula del usuario", HistorialViajesConductorReporte::getCedulaUsuario),
                        new ExcelColumn<>("Nombre del usuario", HistorialViajesConductorReporte::getNombreUsuario),
                        new ExcelColumn<>("Placa del vehiculo", HistorialViajesConductorReporte::getPlacaVehiculo),
                        new ExcelColumn<>("Monto total", HistorialViajesConductorReporte::getMontoTotal)
                );

        return excelReporteService.generarExcel("HistorialViajesConductor", columnas, datos);
    }

    // REPORTES AVANZADOS

    public ByteArrayInputStream obtenerConductoresMasViajesQuePromedio(LocalDate fechaInicio, LocalDate fechaFin) throws IOException {

        List<ConductorViajesPromedioReporte> datos = reportesService.obtenerConductoresConMasViajesQuePromedio(fechaInicio, fechaFin);
        List<ConductorViajesPromedioReporte> datosGenerales = reportesService.obtenerCantidadViajesConductores(fechaInicio, fechaFin);

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

        BarRenderer renderer = new BarRenderer();
        renderer.setSeriesPaint(0, COLOR_AMBAR);
        estilizarGraficaCategoria(grafica, renderer);

        // Línea de promedio
        double promedio = datosGenerales.stream()
                .mapToLong(ConductorViajesPromedioReporte::getTotalViajes)
                .average()
                .orElse(0);

        ValueMarker marker = new ValueMarker(promedio);
        marker.setPaint(COLOR_ROJO);
        marker.setStroke(new BasicStroke(
                2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f,
                new float[]{8f, 4f}, 0f
        ));
        marker.setLabel(String.format("Promedio: %.1f", promedio));
        marker.setLabelFont(FUENTE_MARCADOR);
        marker.setLabelPaint(COLOR_ROJO);
        marker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
        marker.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);

        CategoryPlot plot = grafica.getCategoryPlot();
        plot.addRangeMarker(marker);

        // Etiquetas del eje X rotadas si hay muchos conductores
        if (datos.size() > 6) {
            plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        }

        // Tamaño proporcional: ~80 px por conductor, con un piso y techo
        int ancho = Math.min(1800, Math.max(750, 250 + datos.size() * 80));
        return excelReporteService.generarExcelConGrafica(
                "ConductoresMasViajesPromedio", columnas, datos, grafica, ancho, 520
        );
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

    // ===== ESTILOS =====

    private void estilizarGraficaCategoria(JFreeChart grafica, BarRenderer renderer) {
        grafica.setBackgroundPaint(Color.WHITE);
        grafica.getTitle().setFont(FUENTE_TITULO);
        grafica.getTitle().setPaint(COLOR_OSCURO);

        renderer.setBarPainter(new StandardBarPainter());
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.08);

        CategoryPlot plot = grafica.getCategoryPlot();
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinePaint(COLOR_GRID);
        plot.setRangeGridlineStroke(new BasicStroke(1.0f));

        CategoryAxis xAxis = plot.getDomainAxis();
        xAxis.setLabelFont(FUENTE_EJE);
        xAxis.setTickLabelFont(FUENTE_ETIQUETA);
        xAxis.setAxisLinePaint(COLOR_GRIS);
        xAxis.setTickLabelPaint(COLOR_OSCURO);
        xAxis.setLabelPaint(COLOR_OSCURO);

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setLabelFont(FUENTE_EJE);
        yAxis.setTickLabelFont(FUENTE_ETIQUETA);
        yAxis.setAxisLinePaint(COLOR_GRIS);
        yAxis.setTickLabelPaint(COLOR_OSCURO);
        yAxis.setLabelPaint(COLOR_OSCURO);
    }

    private void estilizarGraficaPie(JFreeChart grafica, DefaultPieDataset<String> dataset) {
        grafica.setBackgroundPaint(Color.WHITE);
        grafica.getTitle().setFont(FUENTE_TITULO);
        grafica.getTitle().setPaint(COLOR_OSCURO);

        @SuppressWarnings("unchecked")
        PiePlot<String> plot = (PiePlot<String>) grafica.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(COLOR_GRID);
        plot.setLabelShadowPaint(null);
        plot.setLabelFont(FUENTE_ETIQUETA);
        plot.setLabelPaint(COLOR_OSCURO);

        // Etiqueta de cada porción: "Nombre: porcentaje%"
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {2}",
                new DecimalFormat("0"),
                new DecimalFormat("0.0%")
        ));

        // Colorear cada porción usando la paleta
        List<String> claves = dataset.getKeys();
        for (int i = 0; i < claves.size(); i++) {
            plot.setSectionPaint(claves.get(i), PALETA[i % PALETA.length]);
        }

        if (grafica.getLegend() != null) {
            grafica.getLegend().setBackgroundPaint(Color.WHITE);
            grafica.getLegend().setItemFont(FUENTE_ETIQUETA);
            grafica.getLegend().setItemPaint(COLOR_OSCURO);
            grafica.getLegend().setFrame(BlockBorder.NONE);
        }
    }
}
