package com.ruta_ya.service.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelReporteService {

    public <T> ByteArrayInputStream generarExcel(String nombreHoja,
                                                 List<ExcelColumn<T>> columnas,
                                                 List<T> datos
    ) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(nombreHoja);

        // HEADER
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columnas.size(); i++) {
            headerRow
                    .createCell(i)
                    .setCellValue(columnas.get(i).getHeader());
        }

        // DATA
        int rowIdx = 1;

        for (T item : datos) {

            Row row = sheet.createRow(rowIdx++);

            for (int col = 0; col < columnas.size(); col++) {

                Object valor = columnas
                        .get(col)
                        .getValueExtractor()
                        .apply(item);

                Cell cell = row.createCell(col);

                if (valor != null) {
                    cell.setCellValue(valor.toString());
                }
            }
        }

        for (int i = 0; i < columnas.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}