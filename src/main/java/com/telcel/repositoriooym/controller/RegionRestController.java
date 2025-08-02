package com.telcel.repositoriooym.controller;

import com.telcel.repositoriooym.entity.Region;
import com.telcel.repositoriooym.response.AreaResponseRest;
import com.telcel.repositoriooym.response.RegionResponseRest;
import com.telcel.repositoriooym.service.IRegionService;
import com.telcel.repositoriooym.utils.AreaExcelExporter;
import com.telcel.repositoriooym.utils.RegionExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

/**
 * @author marcos.hernandez
 */
@CrossOrigin(origins = {"http://localhost:8083"})
@RestController
@RequestMapping("/api/v1")
public class RegionRestController {

    /**
     * Objeto en el contexto de spring para obtener instancias
     */
    @Autowired
    private IRegionService regionService;

    /**
     * Metodo que obtendra todos los objetos o registros de la base de datos
     * @return ResponseEntity con el estatus de la respuesta
     */
    @GetMapping("/regiones")
    public ResponseEntity<RegionResponseRest> listRegiones() {
        return this.regionService.findAll();
    }

    /**
     * Metodo que obtendra un registro de la base de datos por su identificador unico
     * @param idRegion identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @GetMapping("/regiones/{idRegion}")
    public ResponseEntity<RegionResponseRest> listRegionesById(@PathVariable Long idRegion) {
        return this.regionService.findById(idRegion);
    }

    /**
     * Metodo que guarda un objeto en la base de datos
     * @param region Objeto a guardar en la base de datos
     * @return ResponseEntity con el estatus de la respuesta
     */
    @PostMapping("/regiones")
    public ResponseEntity<RegionResponseRest> saveRegion(@RequestBody Region region) {
        return this.regionService.save(region);
    }

    /**
     * Metodo que realiza la actualizacion de un objeto por su identificador unico
     * @param region Objeto que se actualizara
     * @param idRegion identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @PutMapping("/regiones/{idRegion}")
    public ResponseEntity<RegionResponseRest> updateRegion(@RequestBody Region region, @PathVariable Long idRegion) {
        return this.regionService.update(region, idRegion);
    }

    /**
     * Metodo que realiza el borrado de algún registro por su identificador unico
     * @param idRegion identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @DeleteMapping("/regiones/{idRegion}")
    public ResponseEntity<RegionResponseRest> deleteRegionById(@PathVariable Long idRegion) {
        this.regionService.deleteById(idRegion);
        return ResponseEntity.noContent().build(); // Estatus 204 si salio OK
    }

    /**
     * Metodo que realizara la exportacion a excel
     * @param response Objeto de tipo HttpServletResponse
     * @throws IOException Excepcion que se lanzara cuando exista algun error de exportacion
     */
    @GetMapping("/regiones/export/excel")
    public void exportDataExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=datos_departamentos";
        response.setHeader(headerKey, headerValue);

        ResponseEntity<RegionResponseRest> regiones = this.regionService.findAll();

        RegionExcelExporter fileExcelExporter = new RegionExcelExporter(Objects.requireNonNull(regiones.getBody()).getRegionResponse().getRegiones());
        fileExcelExporter.exportData(response);
    }
}
