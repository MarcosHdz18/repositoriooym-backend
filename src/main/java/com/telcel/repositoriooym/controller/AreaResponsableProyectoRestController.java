package com.telcel.repositoriooym.controller;

import com.telcel.repositoriooym.entity.AreaResponsableProyecto;
import com.telcel.repositoriooym.response.AreaResponsableProyectoResponseRest;
import com.telcel.repositoriooym.service.IAreaResponsableProyectoService;
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
public class AreaResponsableProyectoRestController {

    /**
     * Objeto que en el contexto de spring para obtener instancias
     */
    @Autowired
    private IAreaResponsableProyectoService areaResponsableProyectoService;

    /**
     * Metodo que obtendra todos los objetos o registros de la base de datos
     * @return ResponseEntity con el estatus de la respuesta
     */
    @GetMapping("/areas")
    public ResponseEntity<AreaResponsableProyectoResponseRest> listAreas() {
        return this.areaResponsableProyectoService.findAll();
    }

    /**
     * Metodo que obtendra un registro de la base de datos por su identificador unico
     * @param idAreaResponsableProyecto identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @GetMapping("/areas/{idAreaResponsableProyecto}")
    public ResponseEntity<AreaResponsableProyectoResponseRest> listAreasById(@PathVariable Long idAreaResponsableProyecto) {
        return this.areaResponsableProyectoService.findById(idAreaResponsableProyecto);
    }

    /**
     * Metodo que guarda un objeto en la base de datos
     * @param areaResponsableProyecto Objeto a guardar en la base de datos
     * @return ResponseEntity con el estatus de la respuesta
     */
    @PostMapping("/areas")
    public ResponseEntity<AreaResponsableProyectoResponseRest> saveArea(@RequestBody AreaResponsableProyecto areaResponsableProyecto) {
        return this.areaResponsableProyectoService.save(areaResponsableProyecto);
    }

    /**
     * Metodo que realiza la actualizacion de un objeto por su identificador unico
     * @param areaResponsableProyecto Objeto que se actualizara
     * @param idAreaResponsableProyecto identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @PutMapping("/areas/{idAreaResponsableProyecto}")
    public ResponseEntity<AreaResponsableProyectoResponseRest> updateArea(@RequestBody AreaResponsableProyecto areaResponsableProyecto, @PathVariable Long idAreaResponsableProyecto) {
        return this.areaResponsableProyectoService.update(areaResponsableProyecto, idAreaResponsableProyecto);
    }

    /**
     * Metodo que realiza el borrado de algún registro por su identificador unico
     * @param idAreaResponsableProyecto identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @DeleteMapping("/areas/{idAreaResponsableProyecto}")
    public ResponseEntity<AreaResponsableProyectoResponseRest> deleteAreaById(@PathVariable Long idAreaResponsableProyecto) {
        return this.areaResponsableProyectoService.deleteById(idAreaResponsableProyecto);
    }
}
