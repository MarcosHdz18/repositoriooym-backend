package com.telcel.repositoriooym.controller;

import com.telcel.repositoriooym.entity.Proyecto;
import com.telcel.repositoriooym.repository.IProyectoRepository;
import com.telcel.repositoriooym.response.ProyectoResponse;
import com.telcel.repositoriooym.response.ProyectoResponseRest;
import com.telcel.repositoriooym.response.ResponsableResponseRest;
import com.telcel.repositoriooym.service.IProyectoService;
import com.telcel.repositoriooym.service.ISubirArchivoService;
import com.telcel.repositoriooym.utils.ProyectoExcelExporter;
import com.telcel.repositoriooym.utils.ResponsableExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author marcos.hernandez
 */

@CrossOrigin(origins = "http://localhost:8083", exposedHeaders = "Content-Disposition")
@RestController
@RequestMapping("/api/v1")
public class ProyectoRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProyectoRestController.class);

    /**
     * Objeto de tipo IProyectoRepository para el repositorio de la entidad Proyecto
     */
    @Autowired
    private IProyectoRepository proyectoRepository;

    /**
     * Objeto de tipo IProyectoService que interactua con el DAO
     */
    @Autowired
    private IProyectoService proyectoService;

    /**
     * Objeto de tipo ISubirArchivoService que ayudara a subir el archivo a la carpeta uploads
     */
    @Autowired
    private ISubirArchivoService uploadFileService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport(){
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text) || "Pendiente".equalsIgnoreCase(text.trim())) {
                    setValue(null);
                } else {
                    setValue(LocalDate.parse(text.trim(), formatter));
                }
            }
        });

    }

    /**
     * Metodo que realiza la busqueda de todos los proyectos
     * @return ResponseEntity
     */
    @GetMapping("/proyectos")
    public ResponseEntity<ProyectoResponseRest> listaProyectos() {
        ResponseEntity<ProyectoResponseRest> response = this.proyectoService.findAll();
        return response;
    }

    /**
     * Metodo que realiza la busqueda por su identificador unico del proyecto
     * @param idProyecto identificador unico del proyecto
     * @return ResponseEntity
     */
    @GetMapping("/proyectos/{idProyecto}")
    public ResponseEntity<ProyectoResponseRest> buscarProyectoById(@PathVariable Long idProyecto) {
        ResponseEntity<ProyectoResponseRest> response = this.proyectoService.findById(idProyecto);
        return response;
    }

    /**
     * Metodo que realiza la busqueda de un registro por su atributo nombre
     * @param nombre atributo nombre por el que sera buscado el proyecto
     * @return ResponseEntity de tipo ProyectoResponseRest con el proyecto por su atributo nombre
     */
    @GetMapping("/proyectos/filter/{nombre}")
    public ResponseEntity<ProyectoResponseRest> buscarProyectoByNombre(@PathVariable String nombre) {

        ResponseEntity<ProyectoResponseRest> response = this.proyectoService.findByNombre(nombre);

        return response;
    }

    /**
     * Metodo que realiza la persistencia de los datos del objeto de tipo Proyecto
     * @param fileF60 MultipartFile
     * @param nombre String
     * @param fechaLiberacion Date
     * @param responsableId Long
     * @return ResponseEntity
     * @throws IOException Exception IO
     */
    @PostMapping("/proyectos")
    public ResponseEntity<ProyectoResponseRest> guardarProyecto(
            @RequestParam(value = "fileF60", required = false) MultipartFile fileF60,
            @RequestParam(value = "fileLld", required = false) MultipartFile fileLld,
            @RequestParam(value = "fileHld", required = false) MultipartFile fileHld,
            @RequestParam(value = "fileLayout", required = false) MultipartFile fileLayout,
            @RequestParam(value = "fileSla", required = false) MultipartFile fileSla,
            @RequestParam(value = "fileReporteFotografico", required = false) MultipartFile fileReporteFotografico,
            @RequestParam(value = "fileAsignacionFuerzaEspacio", required = false) MultipartFile fileAsignacionFuerzaEspacio,
            @RequestParam(value = "fileInventarioHardware", required = false) MultipartFile fileInventarioHardware,
            @RequestParam(value = "fileAtpFisico", required = false) MultipartFile fileAtpFisico,
            @RequestParam(value = "fileAtpFisicoFirmado", required = false) MultipartFile fileAtpFisicoFirmado,
            @RequestParam(value = "fileAtpLogico", required = false) MultipartFile fileAtpLogico,
            @RequestParam(value = "fileAtpLogicoFirmado", required = false) MultipartFile fileAtpLogicoFirmado,
            @RequestParam(value = "fileReporteTransferenciaOperativa", required = false) MultipartFile fileReporteTransferenciaOperativa,
            @RequestParam(value = "fileCartaResponsivaIaaS", required = false) MultipartFile fileCartaResponsivaIaaS,
            @RequestParam(value = "fileCartaResponsivaPlataforma", required = false) MultipartFile fileCartaResponsivaPlataforma,
            @RequestParam(value = "fileCartaResponsivaStorage", required = false) MultipartFile fileCartaResponsivaStorage,
            @RequestParam(value = "fileCartaResponsivaHa", required = false) MultipartFile fileCartaResponsivaHa,
            @RequestParam(value = "fileCartaResponsivaGsoc", required = false) MultipartFile fileCartaResponsivaGsoc,
            @RequestParam(value = "fileOtros", required = false) MultipartFile fileOtros,
            @RequestParam("nombre") String nombre,
            @RequestParam(value = "fechaLiberacion", required = false) String fechaLiberacion,
            @RequestParam("nodos") String nodos,
            @RequestParam("responsableId") Long responsableId,
            @RequestParam("tipoProyectoId") Long tipoProyectoId,
            @RequestParam("sitioId") Long sitioId) throws IOException {

        // Inicializamos los valores de los objetos por default
        ProyectoResponseRest respuesta = new ProyectoResponseRest();
        respuesta.setMetaList(new ArrayList<>());
        Map<String, String> meta = new HashMap<>();
        respuesta.getMetaList().add(meta);

        try {
            Proyecto proyecto = new Proyecto();

            /**
             * Setear los valores de la data a los objetos pasados por argumento
             */
            proyecto.setNombre(nombre);
            proyecto.setFechaLiberacion(fechaLiberacion);
            proyecto.setNodos(nodos);

            this.proyectoService.save(proyecto, responsableId, tipoProyectoId, sitioId,fechaLiberacion , fileF60, fileLld, fileHld, fileLayout, fileSla, fileReporteFotografico,
                    fileAsignacionFuerzaEspacio, fileInventarioHardware, fileAtpFisico, fileAtpFisicoFirmado, fileAtpLogico, fileAtpLogicoFirmado,
                    fileReporteTransferenciaOperativa, fileCartaResponsivaIaaS, fileCartaResponsivaPlataforma, fileCartaResponsivaStorage, fileCartaResponsivaHa,
                    fileCartaResponsivaGsoc, fileOtros);

            meta.put("code", "00");
            meta.put("data", "Proyecto guardado con éxito");
            return ResponseEntity.ok(respuesta);

        }catch (Exception e) {
            logger.error("Error al persistir el proyecto metodo save", e);
            meta.put("code", "-1");
            meta.put("data", "¡Error al crear el proyecto!");
            return ResponseEntity.ok(respuesta);
        }

    }

    /**
     * Metodo que realiza la actualizacion del registro por su identificador unico
     * @param fileF60
     * @param fileLld
     * @param fileHld
     * @param fileLayout
     * @param fileSla
     * @param fileReporteFotografico
     * @param fileAsignacionFuerzaEspacio
     * @param fileInventarioHardware
     * @param fileAtpFisico
     * @param fileAtpFisicoFirmado
     * @param fileAtpLogico
     * @param fileAtpLogicoFirmado
     * @param fileReporteTransferenciaOperativa
     * @param fileCartaResponsivaIaaS
     * @param fileCartaResponsivaPlataforma
     * @param fileCartaResponsivaStorage
     * @param fileCartaResponsivaHa
     * @param fileCartaResponsivaGsoc
     * @param nombre
     * @param fechaLiberacion
     * @param nodos
     * @param responsableId
     * @param idProyecto
     * @return
     * @throws IOException
     */
    @PutMapping("/proyectos/{idProyecto}")
    public ResponseEntity<ProyectoResponseRest> updateProyecto(
            @RequestParam(value = "fileF60", required = false) MultipartFile fileF60,
            @RequestParam(value = "fileLld", required = false) MultipartFile fileLld,
            @RequestParam(value = "fileHld", required = false) MultipartFile fileHld,
            @RequestParam(value = "fileLayout", required = false) MultipartFile fileLayout,
            @RequestParam(value = "fileSla", required = false) MultipartFile fileSla,
            @RequestParam(value = "fileReporteFotografico", required = false) MultipartFile fileReporteFotografico,
            @RequestParam(value = "fileAsignacionFuerzaEspacio", required = false) MultipartFile fileAsignacionFuerzaEspacio,
            @RequestParam(value = "fileInventarioHardware", required = false) MultipartFile fileInventarioHardware,
            @RequestParam(value = "fileAtpFisico", required = false) MultipartFile fileAtpFisico,
            @RequestParam(value = "fileAtpFisicoFirmado", required = false) MultipartFile fileAtpFisicoFirmado,
            @RequestParam(value = "fileAtpLogico", required = false) MultipartFile fileAtpLogico,
            @RequestParam(value = "fileAtpLogicoFirmado", required = false) MultipartFile fileAtpLogicoFirmado,
            @RequestParam(value = "fileReporteTransferenciaOperativa", required = false) MultipartFile fileReporteTransferenciaOperativa,
            @RequestParam(value = "fileCartaResponsivaIaaS", required = false) MultipartFile fileCartaResponsivaIaaS,
            @RequestParam(value = "fileCartaResponsivaPlataforma", required = false) MultipartFile fileCartaResponsivaPlataforma,
            @RequestParam(value = "fileCartaResponsivaStorage", required = false) MultipartFile fileCartaResponsivaStorage,
            @RequestParam(value = "fileCartaResponsivaHa", required = false) MultipartFile fileCartaResponsivaHa,
            @RequestParam(value = "fileCartaResponsivaGsoc", required = false) MultipartFile fileCartaResponsivaGsoc,
            @RequestParam(value = "fileOtros", required = false) MultipartFile fileOtros,
            @RequestParam("nombre") String nombre,
            @RequestParam(value = "fechaLiberacion", required = false) String fechaLiberacion,
            @RequestParam("nodos") String nodos,
            @RequestParam("responsableId") Long responsableId,
            @RequestParam("tipoProyectoId") Long tipoProyectoId,
            @RequestParam("sitioId") Long sitioId,
            @PathVariable Long idProyecto) throws IOException {

        // Construimos la respuesta con el código de error si no se puede actualizar el proyecto
        ProyectoResponseRest response = new ProyectoResponseRest();
        response.setMetaList(new ArrayList<>());
        Map<String, String> meta = new HashMap<>();
        response.getMetaList().add(meta);

        try {
            // Seteamos los valores del proyecto
            Proyecto proyecto = new Proyecto();
            proyecto.setIdProyecto(idProyecto);
            proyecto.setNombre(nombre);
            proyecto.setFechaLiberacion(fechaLiberacion);
            proyecto.setNodos(nodos);

            this.proyectoService.update(proyecto, responsableId, tipoProyectoId, sitioId, fechaLiberacion, fileF60, fileLld, fileHld, fileLayout, fileSla, fileReporteFotografico,
                    fileAsignacionFuerzaEspacio, fileInventarioHardware, fileAtpFisico, fileAtpFisicoFirmado, fileAtpLogico, fileAtpLogicoFirmado,
                    fileReporteTransferenciaOperativa, fileCartaResponsivaIaaS, fileCartaResponsivaPlataforma, fileCartaResponsivaStorage, fileCartaResponsivaHa,
                    fileCartaResponsivaGsoc,fileOtros);

            meta.put("code", "00");
            meta.put("data", "Proyecto guardado con éxito");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al persistir el proyecto metodo update", e);
            meta.put("code", "-1");
            meta.put("data", "¡Error al actualizar el proyecto!");
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Descarga un archivo de un proyecto según el documento indicado.
     * Ejemplo: GET /api/v1/proyectos/5/archivo/f60
     * @param idProyecto identificador del proyecto
     * @param documento documento a descargar
     * @return una URL con el archivo a descargar
     * @throws MalformedURLException Excepcion si la URL se encuentra mal formada
     */
    @GetMapping(value = "/proyectos/{idProyecto}/archivo/{documento}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> descargarDocumentoProyecto(@PathVariable Long idProyecto, @PathVariable String documento) throws MalformedURLException {

        // 1. Buscar entidad (proyecto)
        Proyecto proyecto = proyectoRepository.findById(idProyecto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));

        // 2. Mapear documento -> nombre de archivo
        String filename;
        switch (documento.toLowerCase()) {
            case "f60": filename = proyecto.getF60(); break;
            case "lld": filename = proyecto.getLld(); break;
            case "hld": filename = proyecto.getHld(); break;
            case "layout": filename = proyecto.getLayout(); break;
            case "sla": filename = proyecto.getSla(); break;
            case "reportefotografico": filename = proyecto.getReporteFotografico(); break;
            case "asignacionfuerzaespacio": filename = proyecto.getAsignacionFuerzaEspacio(); break;
            case "inventariohardware": filename = proyecto.getInventarioHardware(); break;
            case "atpfisico": filename = proyecto.getAtpFisico(); break;
            case "atpfisicofirmado": filename = proyecto.getAtpFisicoFirmado(); break;
            case "atplogico": filename = proyecto.getAtpLogico(); break;
            case "atplogicofirmado": filename = proyecto.getAtpLogicoFirmado(); break;
            case "reportetransferenciaoperativa": filename = proyecto.getReporteTransferenciaOperativa(); break;
            case "cartaresponsivaiaas": filename = proyecto.getCartaResponsivaIaaS(); break;
            case "cartaresponsivaplataforma": filename = proyecto.getCartaResponsivaPlataforma(); break;
            case "cartaresponsivastorage": filename = proyecto.getCartaResponsivaStorage(); break;
            case "cartaresponsivaha": filename = proyecto.getCartaResponsivaHa(); break;
            case "cartaresponsivagsoc": filename = proyecto.getCartaResponsivaGsoc(); break;
            case "otros": filename = proyecto.getOtros(); break;
            default:
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Documento no válido: " + documento);
        }

        if (filename == null || filename.isBlank()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe archivo para el documento" + documento);
        }

        // 3. Cargar recurso y devolver con header de descarga
        Resource recurso = uploadFileService.cargarArchivo(filename);

        // return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"").body(recurso);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + recurso.getFilename() + "\"").body(recurso);
    }

    /**
     * Metodo que realiza el borrado de un registro por su identificador unico
     * @param idProyecto identificador unico del proyecto a eliminar
     * @return ResponseEntity de tipo ProyectoResponseRest
     */
    @DeleteMapping("/proyectos/{idProyecto}")
    public ResponseEntity<Void> deleteProyectoById(@PathVariable Long idProyecto) {

        proyectoService.deleteProyecto(idProyecto);

        return ResponseEntity.noContent().build();
    }

    /**
     * Metodo que realizara la exportacion a excel
     * @param response Objeto de tipo HttpServletResponse
     * @throws IOException Excepcion que se lanzara cuando exista algun error de exportacion
     */
    @GetMapping("/proyectos/export/excel")
    public void exportDataExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=datos_departamentos";
        response.setHeader(headerKey, headerValue);

        ResponseEntity<ProyectoResponseRest> proyectos = this.proyectoService.findAll();

        ProyectoExcelExporter fileExcelExporter = new ProyectoExcelExporter(Objects.requireNonNull(proyectos.getBody()).getProyectoResponse().getProyectos());
        fileExcelExporter.exportData(response);
    }
}
