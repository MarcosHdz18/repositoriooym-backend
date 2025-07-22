package com.telcel.repositoriooym.utils;

import com.telcel.repositoriooym.entity.Responsable;
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

public class ResponsableExcelExporter {

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
    private List<Responsable> responsableList;

    /**
     * Constructor sobrecargado con la lista a iterar y se inicializa el objeto workbook
     *
     * @param responsableList lista de objetos de tipo Area
     */
    public ResponsableExcelExporter(List<Responsable> responsableList) {
        this.workbook = new XSSFWorkbook();
        this.responsableList = responsableList;
    }

    /**
     * Metodo que escribe en la cabecera del archivo de Excel
     */
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Responsables de proyectos");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);

        style.setFont(font);

        // Creacion de celdas en el libro
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Nombre", style);
        createCell(row, 2, "Apellido paterno", style);
        createCell(row, 3, "Apellido materno", style);
        createCell(row, 4, "Numero de empleado", style);
        createCell(row, 5, "Departamento", style);
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
        for (Responsable responsable: responsableList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(responsable.getIdResponsable()), style);
            createCell(row, columnCount++, responsable.getNombre(), style);
            createCell(row, columnCount++, responsable.getApellidoPaterno(), style);
            createCell(row, columnCount++, responsable.getApellidoMaterno(), style);
            createCell(row, columnCount++, responsable.getNumeroEmpleado(), style);
            createCell(row, columnCount++, responsable.getArea().getNombre(), style);
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
