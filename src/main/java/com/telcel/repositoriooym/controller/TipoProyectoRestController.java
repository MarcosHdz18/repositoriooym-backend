package com.telcel.repositoriooym.controller;

import com.telcel.repositoriooym.entity.TipoProyecto;
import com.telcel.repositoriooym.response.TipoProyectoResponseRest;
import com.telcel.repositoriooym.service.ITipoProyectoService;
import com.telcel.repositoriooym.utils.TipoProyectoExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

/**
 * @author marcos.hernandez
 */
@RestController
@RequestMapping("/api/v1")
public class TipoProyectoRestController {

    /**
     * Objeto en el contexto de spring para obtener instancias
     */
    @Autowired
    private ITipoProyectoService tipoProyectoService;

    /**
     * Metodo que obtendra todos los objetos o registros de la base de datos
     * @return ResponseEntity con el estatus de la respuesta
     */
    @GetMapping("/tiposProyecto")
    public ResponseEntity<TipoProyectoResponseRest> listaTiposProyectos() {
        return this.tipoProyectoService.findAll();
    }

    /**
     * Metodo que obtendra un registro de la base de datos por su identificador unico
     * @param idTipoProyecto identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @GetMapping("/tiposProyecto/{idTipoProyecto}")
    public ResponseEntity<TipoProyectoResponseRest> listaTiposProyectoById(@PathVariable Long idTipoProyecto) {
        return this.tipoProyectoService.findById(idTipoProyecto);
    }

    /**
     * Metodo que guarda un objeto en la base de datos
     * @param tipoProyecto Objeto a guardar en la base de datos
     * @return ResponseEntity con el estatus de la respuesta
     */
    @PostMapping("/tiposProyecto")
    public ResponseEntity<TipoProyectoResponseRest> guardarTipoProyecto(@RequestBody TipoProyecto tipoProyecto) {
        return this.tipoProyectoService.save(tipoProyecto);
    }

    /**
     * Metodo que realiza la actualizacion de un objeto por su identificador unico
     * @param tipoProyecto Objeto que se actualizara
     * @param idTipoProyecto identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @PutMapping("/tiposProyecto/{idTipoProyecto}")
    public ResponseEntity<TipoProyectoResponseRest> actualizarTipoProyecto(@RequestBody TipoProyecto tipoProyecto, @PathVariable Long idTipoProyecto) {
        return this.tipoProyectoService.update(tipoProyecto, idTipoProyecto);
    }

    /**
     * Metodo que realiza el borrado de algún registro por su identificador unico
     * @param idTipoProyecto identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @DeleteMapping("/tiposProyecto/{idTipoProyecto}")
    public ResponseEntity<TipoProyectoResponseRest> deleteTipoProyectoById(@PathVariable Long idTipoProyecto) {
        this.tipoProyectoService.deleteById(idTipoProyecto);
        return ResponseEntity.noContent().build(); // Estatus 204 si salio OK
    }

    /**
     * Metodo que realizara la exportacion a excel
     * @param response Objeto de tipo HttpServletResponse
     * @throws IOException Excepcion que se lanzara cuando exista algun error de exportacion
     */
    @GetMapping("/tiposProyecto/export/excel")
    public void exportDataExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=tipos_proyectos";
        response.setHeader(headerKey, headerValue);

        ResponseEntity<TipoProyectoResponseRest> tiposProyecto = this.tipoProyectoService.findAll();

        TipoProyectoExcelExporter fileExcelExporter = new TipoProyectoExcelExporter(Objects.requireNonNull(tiposProyecto.getBody()).getTipoProyectoResponse().getTiposProyecto());
        fileExcelExporter.exportData(response);
    }
}
