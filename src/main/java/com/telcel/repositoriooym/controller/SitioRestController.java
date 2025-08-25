package com.telcel.repositoriooym.controller;

import com.telcel.repositoriooym.entity.Responsable;
import com.telcel.repositoriooym.entity.Sitio;
import com.telcel.repositoriooym.response.ResponsableResponseRest;
import com.telcel.repositoriooym.response.SitioResponseRest;
import com.telcel.repositoriooym.service.ISitioService;
import com.telcel.repositoriooym.utils.ResponsableExcelExporter;
import com.telcel.repositoriooym.utils.SitioExcelExporter;
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
public class SitioRestController {

    /**
     * Inyeccion de dependencia para interactuar con los metodos implementados
     */
    @Autowired
    private ISitioService sitioService;

    /**
     * Metodo que obtiene la lista de todos los sitios
     * @return Response Entity con el estatus de la respuesta
     */
    @GetMapping("/sitios")
    public ResponseEntity<SitioResponseRest> listSitios() {
        return this.sitioService.findAll();
    }

    /**
     * Metodo que realiza la busqueda por su identificador unico
     * @param idSitio identificador unico a buscar
     * @return Response Entity con el estatus de la respuesta
     */
    @GetMapping("/sitios/{idSitio}")
    public ResponseEntity<SitioResponseRest> listSitioById(@PathVariable Long idSitio) {
        return this.sitioService.findById(idSitio);
    }

    /**
     * Metodo que persiste un objeto de tipo Responsable en la base de datos
     * @param nombre Nombre del responsable
     * @param treeChar codigo del sitio
     * @param direccion direccion del sitio
     * @param nombreContacto nombre del contacto del sitio
     * @param telefonoContacto telefono del contacto del sitio
     * @param correoContacto correo del contacto del sitio
     * @return Response Entity con el estatus de la respuesta
     * @throws IOException Excepcion que pueda lanzar
     */
    @PostMapping("/sitios")
    public ResponseEntity<SitioResponseRest> saveSitio(@RequestParam("nombre") String nombre,
                                                                   @RequestParam("treeChar") String treeChar,
                                                                   @RequestParam("direccion") String direccion,
                                                                   @RequestParam("nombreContacto") String nombreContacto,
                                                                   @RequestParam("telefonoContacto") String telefonoContacto,
                                                                   @RequestParam("correoContacto") String correoContacto,
                                                                   @RequestParam("regionId") Long regionId) throws IOException {

        Sitio sitio = new Sitio();
        sitio.setNombre(nombre);
        sitio.setTreeChar(treeChar);
        sitio.setDireccion(direccion);
        sitio.setNombreContacto(nombreContacto);
        sitio.setTelefonoContacto(telefonoContacto);
        sitio.setCorreoContacto(correoContacto);

        return this.sitioService.save(sitio, regionId);
    }

    /**
     * Metodo que persiste un objeto de tipo Responsable en la base de datos
     * @param nombre Nombre del responsable
     * @param treeChar codigo del sitio
     * @param direccion direccion del sitio
     * @param nombreContacto nombre del contacto del sitio
     * @param telefonoContacto telefono del contacto del sitio
     * @param correoContacto correo del contacto del sitio
     * @return Response Entity con el estatus de la respuesta
     * @throws IOException Excepcion que pueda lanzar
     */
    @PutMapping("/sitios/{idSitio}")
    public ResponseEntity<SitioResponseRest> updateSitio(@RequestParam("nombre") String nombre,
                                                         @RequestParam("treeChar") String treeChar,
                                                         @RequestParam("direccion") String direccion,
                                                         @RequestParam("nombreContacto") String nombreContacto,
                                                         @RequestParam("telefonoContacto") String telefonoContacto,
                                                         @RequestParam("correoContacto") String correoContacto,
                                                         @RequestParam("regionId") Long regionId,
                                                         @PathVariable Long idSitio) throws IOException {

        Sitio sitio = new Sitio();
        sitio.setNombre(nombre);
        sitio.setTreeChar(treeChar);
        sitio.setDireccion(direccion);
        sitio.setNombreContacto(nombreContacto);
        sitio.setTelefonoContacto(telefonoContacto);
        sitio.setCorreoContacto(correoContacto);

        return this.sitioService.update(sitio, idSitio, regionId);
    }

    /**
     * Metodo que se utiliza para el borrado de un registro por su identificador unico
     * @param idSitio Identificador unico
     * @return Response Entity con el estatus de la respuesta
     */
    @DeleteMapping("/sitios/{idSitio}")
    public ResponseEntity<SitioResponseRest> deleteSitio(@PathVariable Long idSitio) {
        return this.sitioService.deleteById(idSitio);
    }

    /**
     * Metodo que realizara la exportacion a excel
     * @param response Objeto de tipo HttpServletResponse
     * @throws IOException Excepcion que se lanzara cuando exista algun error de exportacion
     */
    @GetMapping("/sitios/export/excel")
    public void exportDataExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=datos_departamentos";
        response.setHeader(headerKey, headerValue);

        ResponseEntity<SitioResponseRest> sitios = this.sitioService.findAll();

        SitioExcelExporter fileExcelExporter = new SitioExcelExporter(Objects.requireNonNull(sitios.getBody()).getSitioResponse().getSitios());
        fileExcelExporter.exportData(response);
    }
}
