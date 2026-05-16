package com.ruta_ya.service.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelReporteService {

    public <T> ByteArrayInputStream generarExcel(String nombreHoja,
                                                 List<ExcelColumn<T>> columnas,
                                                 List<T> datos) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        llenarHojaDatos(workbook, nombreHoja, columnas, datos);
        return serializarWorkbook(workbook);
    }

    public <T> ByteArrayInputStream generarExcelConGrafica(String nombreHoja,
                                                           List<ExcelColumn<T>> columnas,
                                                           List<T> datos,
                                                           JFreeChart grafica) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        llenarHojaDatos(workbook, nombreHoja, columnas, datos);
        insertarGrafica(workbook, grafica, datos.size() + 3);
        return serializarWorkbook(workbook);
    }

    private <T> void llenarHojaDatos(XSSFWorkbook workbook, String nombreHoja,
                                     List<ExcelColumn<T>> columnas, List<T> datos) {
        Sheet sheet = workbook.createSheet(nombreHoja);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnas.size(); i++) {
            headerRow.createCell(i).setCellValue(columnas.get(i).getHeader());
        }

        int rowIdx = 1;
        for (T item : datos) {
            Row row = sheet.createRow(rowIdx++);
            for (int col = 0; col < columnas.size(); col++) {
                Object valor = columnas.get(col).getValueExtractor().apply(item);
                Cell cell = row.createCell(col);
                if (valor != null) {
                    cell.setCellValue(valor.toString());
                }
            }
        }

        for (int i = 0; i < columnas.size(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void insertarGrafica(XSSFWorkbook workbook, JFreeChart grafica, int filaInicio) throws IOException {
        ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(chartOut, grafica, 800, 500);
        int picIdx = workbook.addPicture(chartOut.toByteArray(), Workbook.PICTURE_TYPE_PNG);

        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, 0, filaInicio, 8, filaInicio + 20);
        drawing.createPicture(anchor, picIdx);
    }

    private ByteArrayInputStream serializarWorkbook(XSSFWorkbook workbook) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
