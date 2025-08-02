package com.telcel.repositoriooym.utils;

import com.telcel.repositoriooym.entity.Proyecto;
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

public class ProyectoExcelExporter {


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
    private List<Proyecto> proyectoList;

    /**
     * Constructor sobrecargado con la lista a iterar y se inicializa el objeto workbook
     *
     * @param proyectoList lista de objetos de tipo Area
     */
    public ProyectoExcelExporter(List<Proyecto> proyectoList) {
        this.workbook = new XSSFWorkbook();
        this.proyectoList = proyectoList;
    }

    /**
     * Metodo que escribe en la cabecera del archivo de Excel
     */
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Proyectos");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);

        style.setFont(font);

        // Creacion de celdas en el libro
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Nombre", style);
        createCell(row, 2, "Fecha de liberación", style);
        createCell(row, 3, "Año", style);
        createCell(row, 4, "Nodos", style);
        createCell(row, 5, "Responsable", style);
        createCell(row, 6,"Region", style);
        createCell(row, 7, "Sitio", style);
        createCell(row, 8, "Tipo de proyecto", style);
        createCell(row, 9, "Archivo F60", style);
        createCell(row, 10, "Archivo LLD", style);
        createCell(row, 11, "Archivo HLD", style);
        createCell(row, 12, "Archivo Layout", style);
        createCell(row, 13, "Archivo SLA", style);
        createCell(row, 14, "Archivo RTO", style);
        createCell(row, 15, "Archivo ATP Físico", style);
        createCell(row, 16, "Archivo ATP Físico Firmado", style);
        createCell(row, 17, "Archivo ATP Lógico", style);
        createCell(row, 18, "Archivo ATP Lógico Firmado", style);
        createCell(row, 19, "Archivo Reporte Fotográfico", style);
        createCell(row, 20, "Archivo Asignación Fuerza-Espacio", style);
        createCell(row, 21, "Archivo Inventario Hardware", style);
        createCell(row, 22, "Archivo Carta Responsiva IaaS", style);
        createCell(row, 23, "Archivo Carta Responsiva Plataforma", style);
        createCell(row, 24, "Archivo Carta Responsiva Storage", style);
        createCell(row, 25, "Archivo Carta Responsiva HA", style);
        createCell(row, 26, "Archivo Carta Responsiva GSOC", style);
        createCell(row, 27, "Archivo Otros", style);
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
        for (Proyecto proyecto: proyectoList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(proyecto.getIdProyecto()), style);
            createCell(row, columnCount++, proyecto.getNombre(), style);
            createCell(row, columnCount++, proyecto.getFechaLiberacion(), style);
            createCell(row, columnCount++, proyecto.getAnio(), style);
            createCell(row, columnCount++, proyecto.getNodos(), style);
            createCell(row, columnCount++, proyecto.getResponsableProyecto().getNombre(), style);
            createCell(row, columnCount++, proyecto.getRegion().getNombre(), style);
            createCell(row, columnCount++, proyecto.getSitio().getNombre(), style);
            createCell(row, columnCount++, proyecto.getTipoProyecto(), style);
            createCell(row, columnCount++, proyecto.getF60(), style);
            createCell(row, columnCount++, proyecto.getLld(), style);
            createCell(row, columnCount++, proyecto.getHld(), style);
            createCell(row, columnCount++, proyecto.getLayout(), style);
            createCell(row, columnCount++, proyecto.getSla(), style);
            createCell(row, columnCount++, proyecto.getReporteTransferenciaOperativa(), style);
            createCell(row, columnCount++, proyecto.getAtpFisico(), style);
            createCell(row, columnCount++, proyecto.getAtpFisicoFirmado(), style);
            createCell(row, columnCount++, proyecto.getAtpLogico(), style);
            createCell(row, columnCount++, proyecto.getAtpLogicoFirmado(), style);
            createCell(row, columnCount++, proyecto.getReporteFotografico(), style);
            createCell(row, columnCount++, proyecto.getAsignacionFuerzaEspacio(), style);
            createCell(row, columnCount++, proyecto.getInventarioHardware(), style);
            createCell(row, columnCount++, proyecto.getCartaResponsivaIaaS(), style);
            createCell(row, columnCount++, proyecto.getCartaResponsivaPlataforma(), style);
            createCell(row, columnCount++, proyecto.getCartaResponsivaStorage(), style);
            createCell(row, columnCount++, proyecto.getCartaResponsivaHa(), style);
            createCell(row, columnCount++, proyecto.getCartaResponsivaGsoc(), style);
            createCell(row, columnCount++, proyecto.getOtros(), style);
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
