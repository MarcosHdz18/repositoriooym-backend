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

    /**
     * Metodo que obtiene la lista de todos los responsables
     * @return Response Entity con el estatus de la respuesta
     */
    @GetMapping("/responsables")
    public ResponseEntity<ResponsableResponseRest> listResponsables() {
        return this.responsableService.findAll();
    }

    /**
     * Metodo que realiza la busqueda por su identificador unico
     * @param idResponsable identificador unico a buscar
     * @return Response Entity con el estatus de la respuesta
     */
    @GetMapping("/responsables/{idResponsable}")
    public ResponseEntity<ResponsableResponseRest> listResponsableById(@PathVariable Long idResponsable) {
        return this.responsableService.findById(idResponsable);
    }

    /**
     * Metodo que persiste un objeto de tipo Responsable en la base de datos
     * @param nombre Nombre del responsable
     * @param apellidoPaterno Apellido paterno del responsable
     * @param apellidoMaterno Apellido materno del responsable
     * @param numeroEmpleado Numero de empleado del responsable
     * @param areaId Area del responsable
     * @return Response Entity con el estatus de la respuesta
     * @throws IOException Excepcion que pueda lanzar
     */
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

    /**
     * Metodo que realiza la actualización del responsable
     * @param nombre Nombre del responsable
     * @param apellidoPaterno Apellido paterno del responsable
     * @param apellidoMaterno Apellido materno del responsable
     * @param numeroEmpleado Numero de empleado del responsable
     * @param areaId Area del responsable
     * @param idResponsable Identificador unico del responsable
     * @return Response Entity con el estatus de la respuesta
     * @throws IOException Excepcion que pueda lanzar
     */
    @PutMapping("/responsables/{idResponsable}")
    public ResponseEntity<ResponsableResponseRest> updateResponsable(@RequestParam("nombre") String nombre,
                                                                     @RequestParam("apellidoPaterno") String apellidoPaterno,
                                                                     @RequestParam("apellidoMaterno") String apellidoMaterno,
                                                                     @RequestParam("numeroEmpleado") Integer numeroEmpleado,
                                                                     @RequestParam("areaId") Long areaId,
                                                                     @PathVariable Long idResponsable) throws IOException {

        Responsable responsable = new Responsable();
        responsable.setNombre(nombre);
        responsable.setApellidoPaterno(apellidoPaterno);
        responsable.setApellidoMaterno(apellidoMaterno);
        responsable.setNumeroEmpleado(numeroEmpleado);

        return this.responsableService.update(responsable, areaId, idResponsable);
    }

    /**
     * Metodo que se utiliza para el borrado de un registro por su identificador unico
     * @param idResponsable Identificador unico
     * @return Response Entity con el estatus de la respuesta
     */
    @DeleteMapping("/responsables/{idResponsable}")
    public ResponseEntity<ResponsableResponseRest> deleteResponsable(@PathVariable Long idResponsable) {
        return this.responsableService.deleteById(idResponsable);
    }
}
