package com.telcel.repositoriooym.controller;

import com.telcel.repositoriooym.entity.Responsable;
import com.telcel.repositoriooym.response.ResponsableResponseRest;
import com.telcel.repositoriooym.service.IResponsableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author marcos.hernandez
 *
 */

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
public class ResponsableRestController {

    /**
     * Inyeccion de dependencia para interactuar con los metodos implementados
     */
    @Autowired
    private IResponsableService responsableService;

    @GetMapping("/responsables")
    public ResponseEntity<ResponsableResponseRest> listResponsables() {
        return this.responsableService.findAll();
    }

    @PostMapping("/responsables")
    public ResponseEntity<ResponsableResponseRest> saveResponsable(@RequestParam("nombre") String nombre,
                                                                   @RequestParam("apellidoPaterno") String apellidoPaterno,
                                                                   @RequestParam("apellidoMaterno") String apellidoMaterno,
                                                                   @RequestParam("numeroEmpleado") Integer numeroEmpleado,
                                                                   @RequestParam("areaId") Long areaId) throws IOException {

        Responsable responsable = new Responsable();
        responsable.setNombre(nombre);
        responsable.setApellidoPaterno(apellidoPaterno);
        responsable.setApellidoMaterno(apellidoMaterno);
        responsable.setNumeroEmpleado(numeroEmpleado);

        return this.responsableService.save(responsable, areaId);
    }



}
