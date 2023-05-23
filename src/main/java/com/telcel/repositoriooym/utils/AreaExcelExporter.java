package com.telcel.repositoriooym.utils;

import com.telcel.repositoriooym.entity.Area;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

import static org.apache.poi.ss.util.CellUtil.createCell;

/**
 * @author marcos.hernandez
 */

public class AreaExcelExporter {

    /**
     * Objeto para libro de Excel
     */
    private XSSFWorkbook workbook;

    /**
     * Objeto para hoja de Excel
     */
    private XSSFSheet sheet;

    /**
     * Arreglo de objetos a iterar
     */
    private List<Area> areaList;

    /**
     * Constructor sobrecargado con la lista a iterar y se inicializa el objeto workbook
     * @param areaList lista de objetos de tipo Area
     */
    public AreaExcelExporter(List<Area> areaList) {
        this.workbook = new XSSFWorkbook();
        this.areaList = areaList;
    }

    /**
     * Metodo que escribe en la cabecera del archivo de Excel
     */
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Departamentos");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);

        style.setFont(font);

        // Creacion de celdas en el libro
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Nombre", style);
        createCell(row, 2, "Descripción", style);
    }

    /**
     * Metodo utilizado para crear la celda en el libro
     * @param row fila del libro
     * @param columnCount conteo de columnas
     * @param value valor de la celda
     * @param style estilo de la celda
     */
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if(value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if(value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }

    /**
     * Metodo que sera utilizado para integrar los datos en el libro de excel
     */
    private void writeDataLines() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        // Iteracion de la lista de objetos de tipo Category
        for (Area area: areaList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(area.getIdArea()), style);
            createCell(row, columnCount++, area.getNombre(), style);
            createCell(row, columnCount++, area.getDescripcion(), style);
        }
    }

    /**
     * Metodo que realizara la exportacion de la data al libro de excel
     * @param response objeto de tipo HttpServletResponse
     * @throws IOException Excepcion que se lanzara cuando no pueda realizar la exportacion
     */
    public void exportData(HttpServletResponse response) throws IOException {
        // Escribe la cabecera del archivo
        writeHeaderLine();
        // Escribe la data en el archivo
        writeDataLines();

        ServletOutputStream servletOutputStream = response.getOutputStream();
        workbook.write(servletOutputStream);
        workbook.close();

        servletOutputStream.close();
    }
}
