package com.telcel.repositoriooym.controller;

import com.telcel.repositoriooym.entity.Area;
import com.telcel.repositoriooym.response.AreaResponseRest;
import com.telcel.repositoriooym.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author marcos.hernandez
 *
 */
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class AreaRestController {

    /**
     * Objeto en el contexto de spring para obtener instancias
     */
    @Autowired
    private IAreaService areaService;

    /**
     * Metodo que obtendra todos los objetos o registros de la base de datos
     * @return ResponseEntity con el estatus de la respuesta
     */
    @GetMapping("/areas")
    public ResponseEntity<AreaResponseRest> listAreas() {
        return this.areaService.findAll();
    }

    /**
     * Metodo que obtendra un registro de la base de datos por su identificador unico
     * @param idArea identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @GetMapping("/areas/{idArea}")
    public ResponseEntity<AreaResponseRest> listAreasById(@PathVariable Long idArea) {
        return this.areaService.findById(idArea);
    }

    /**
     * Metodo que guarda un objeto en la base de datos
     * @param area Objeto a guardar en la base de datos
     * @return ResponseEntity con el estatus de la respuesta
     */
    @PostMapping("/areas")
    public ResponseEntity<AreaResponseRest> saveArea(@RequestBody Area area) {
        return this.areaService.save(area);
    }

    /**
     * Metodo que realiza la actualizacion de un objeto por su identificador unico
     * @param area Objeto que se actualizara
     * @param idArea identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @PutMapping("/areas/{idArea}")
    public ResponseEntity<AreaResponseRest> updateArea(@RequestBody Area area, @PathVariable Long idArea) {
        return this.areaService.update(area, idArea);
    }

    /**
     * Metodo que realiza el borrado de algún registro por su identificador unico
     * @param idArea identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @DeleteMapping("/areas/{idArea}")
    public ResponseEntity<AreaResponseRest> deleteAreaById(@PathVariable Long idArea) {
        return this.areaService.deleteById(idArea);
    }
}
