package com.telcel.repositoriooym.service.impl;

import com.telcel.repositoriooym.controller.ProyectoRestController;
import com.telcel.repositoriooym.entity.*;
import com.telcel.repositoriooym.repository.*;
import com.telcel.repositoriooym.response.ProyectoResponse;
import com.telcel.repositoriooym.response.ProyectoResponseRest;
import com.telcel.repositoriooym.service.IProyectoService;
import com.telcel.repositoriooym.service.ISubirArchivoService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author marcos.hernandez
 */

@Service
public class ProyectoServiceImpl implements IProyectoService {

    private static final Logger logger = LoggerFactory.getLogger(ProyectoRestController.class);

    /**
     * Objeto de tipo IProyectoRepository con el CRUD respectivo
     */
    @Autowired
    private IProyectoRepository proyectoRepository;

    /**
     * Objeto de tipo IResponsableRepository con el CRUD respectivo
     */

    @Autowired
    private IResponsableRepository responsableRepository;

    /**
     * Objeto de tipo ITipoProyectoRepository con el CRUD respectivo
     */
    @Autowired
    private ITipoProyectoRepository tipoProyectoRepository;

    /**
     * Objeto de tipo ISitioRepository con el CRUD respectivo
     */
    @Autowired
    private ISitioRepository sitioRepository;

    /**
     * Objeto de tipo IClienteRepository con el CRUD respectivo
     */
    @Autowired
    private IClienteRepository clienteRepository;

    /**
     * Objeto de tipo ISubirArchivoService con las funcionalidades para subir los archivos
     */
    @Autowired
    private ISubirArchivoService uploadFileService;

    /**
     * Objeto para la auditoria correspondiente
     */
    @Autowired
    private IBitacoraRepository bitacoraRepository;

    /**
     * Objeto para la carga de archivos adjuntos del proyecto
     */
    @Autowired
    private IDocumentoAdjuntoRepository adjuntoRepository;

    /**
     * Carpeta donde se almacenaran los archivos a subir
     */
    @Value("${folder.location}")
    private String documentacionProyectos;

    /**
     * Metodo que lista todos los objetos de tipo Proyecto obtenidos de la base de datos
     * @return ResponseEntity con todos los objetos de tipo Proyecto
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProyectoResponseRest> findAll() {

        /**
         * Lista de objetos de tipo proyecto
         */
        ProyectoResponseRest response = new ProyectoResponseRest();
        List<Proyecto> proyectos = new ArrayList<>();
        List<Proyecto> listaProyectos = new ArrayList<>();

        try {
            listaProyectos = (List<Proyecto>) this.proyectoRepository.findAll();

            if (listaProyectos.size() > 0) {
                listaProyectos.stream().forEach((proyecto) -> {

                    String f60 = proyecto.getF60();
                    String lld = proyecto.getLld();
                    String hld = proyecto.getHld();
                    String memoriaTecnica = proyecto.getMemoriaTecnica();
                    String layout = proyecto.getLayout();
                    String sla = proyecto.getSla();
                    String reporteFotografico = proyecto.getReporteFotografico();
                    String asignacionFuerzaEspacio = proyecto.getAsignacionFuerzaEspacio();
                    String etiquetado = proyecto.getEtiquetado();
                    String planos = proyecto.getPlanos();
                    String proyectoEjecutivo = proyecto.getProyectoEjecutivo();
                    String inventarioHardware = proyecto.getInventarioHardware();
                    String nodos = proyecto.getNodos();
                    String atpFisico = proyecto.getAtpFisico();
                    String atpFisicoFirmado = proyecto.getAtpFisicoFirmado();
                    String atpLogico = proyecto.getAtpLogico();
                    String reporteTransferenciaOperativa = proyecto.getReporteTransferenciaOperativa();
                    String cartaResponsivaIaaS = proyecto.getCartaResponsivaIaaS();
                    String cartaResponsivaPlataforma = proyecto.getCartaResponsivaPlataforma();
                    String cartaResponsivaStorage = proyecto.getCartaResponsivaStorage();
                    String cartaResponsivaHa = proyecto.getCartaResponsivaHa();
                    String cartaResponsivaGsoc = proyecto.getCartaResponsivaGsoc();
                    String cartaResponsivaLlaves = proyecto.getCartaResponsivaLlaves();
                    String otros = proyecto.getOtros();

                    proyecto.setF60(f60);
                    proyecto.setLld(lld);
                    proyecto.setHld(hld);
                    proyecto.setMemoriaTecnica(memoriaTecnica);
                    proyecto.setLayout(layout);
                    proyecto.setSla(sla);
                    proyecto.setReporteFotografico(reporteFotografico);
                    proyecto.setAsignacionFuerzaEspacio(asignacionFuerzaEspacio);
                    proyecto.setEtiquetado(etiquetado);
                    proyecto.setPlanos(planos);
                    proyecto.setProyectoEjecutivo(proyectoEjecutivo);
                    proyecto.setInventarioHardware(inventarioHardware);
                    proyecto.setNodos(nodos);
                    proyecto.setAtpFisico(atpFisico);
                    proyecto.setAtpFisicoFirmado(atpFisicoFirmado);
                    proyecto.setAtpLogico(atpLogico);
                    proyecto.setReporteTransferenciaOperativa(reporteTransferenciaOperativa);
                    proyecto.setCartaResponsivaIaaS(cartaResponsivaIaaS);
                    proyecto.setCartaResponsivaPlataforma(cartaResponsivaPlataforma);
                    proyecto.setCartaResponsivaStorage(cartaResponsivaStorage);
                    proyecto.setCartaResponsivaHa(cartaResponsivaHa);
                    proyecto.setCartaResponsivaGsoc(cartaResponsivaGsoc);
                    proyecto.setCartaResponsivaLlaves(cartaResponsivaLlaves);
                    proyecto.setOtros(otros);

                    proyectos.add(proyecto);
                });
            }

            response.getProyectoResponse().setProyectos(proyectos);
            response.setMetadata("Respuesta exitosa", "00", "Proyectos encontrados con éxito");

        } catch (Exception ex) {
            ex.getStackTrace();
            response.setMetadata("Respuesta no exitosa", "-1", "Error al consultar los registros");
            return new ResponseEntity<ProyectoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProyectoResponseRest>(response, HttpStatus.OK);
    }

    /**
     * @param idProyecto identificador unico del proyecto a buscar
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProyectoResponseRest> findById(Long idProyecto) {

        ProyectoResponseRest response = new ProyectoResponseRest();
        List<Proyecto> proyectos = new ArrayList<>();

        try {
            // Buscar el proyecto por su identificador unico
            Optional<Proyecto> proyecto = this.proyectoRepository.findById(idProyecto);

            if (proyecto.isPresent()) {
                String f60 = proyecto.get().getF60();
                String lld = proyecto.get().getLld();
                String hld = proyecto.get().getHld();
                String memoriaTecnica = proyecto.get().getMemoriaTecnica();
                String layout = proyecto.get().getLayout();
                String sla = proyecto.get().getSla();
                String reporteFotografico = proyecto.get().getReporteFotografico();
                String asignacionFuerzaEspacio = proyecto.get().getAsignacionFuerzaEspacio();
                String etiquetado = proyecto.get().getEtiquetado();
                String planos = proyecto.get().getPlanos();
                String proyectoEjecutivo = proyecto.get().getProyectoEjecutivo();
                String inventarioHardware = proyecto.get().getInventarioHardware();
                String nodos = proyecto.get().getNodos();
                String atpFisico = proyecto.get().getAtpFisico();
                String atpFisicoFirmado = proyecto.get().getAtpFisicoFirmado();
                String atpLogico = proyecto.get().getAtpLogico();
                String reporteTransferenciaOperativa = proyecto.get().getReporteTransferenciaOperativa();
                String cartaResponsivaIaaS = proyecto.get().getCartaResponsivaIaaS();
                String cartaResponsivaPlataforma = proyecto.get().getCartaResponsivaPlataforma();
                String cartaResponsivaStorage = proyecto.get().getCartaResponsivaStorage();
                String cartaResponsivaHa = proyecto.get().getCartaResponsivaHa();
                String cartaResponsivaGsoc = proyecto.get().getCartaResponsivaGsoc();
                String cartaResponsivaLlaves = proyecto.get().getCartaResponsivaLlaves();
                String otros = proyecto.get().getOtros();

                proyecto.get().setF60(f60);
                proyecto.get().setLld(lld);
                proyecto.get().setHld(hld);
                proyecto.get().setMemoriaTecnica(memoriaTecnica);
                proyecto.get().setLayout(layout);
                proyecto.get().setSla(sla);
                proyecto.get().setReporteFotografico(reporteFotografico);
                proyecto.get().setAsignacionFuerzaEspacio(asignacionFuerzaEspacio);
                proyecto.get().setEtiquetado(etiquetado);
                proyecto.get().setPlanos(planos);
                proyecto.get().setProyectoEjecutivo(proyectoEjecutivo);
                proyecto.get().setInventarioHardware(inventarioHardware);
                proyecto.get().setNodos(nodos);
                proyecto.get().setAtpFisico(atpFisico);
                proyecto.get().setAtpFisicoFirmado(atpFisicoFirmado);
                proyecto.get().setAtpLogico(atpLogico);
                proyecto.get().setReporteTransferenciaOperativa(reporteTransferenciaOperativa);
                proyecto.get().setCartaResponsivaIaaS(cartaResponsivaIaaS);
                proyecto.get().setCartaResponsivaPlataforma(cartaResponsivaPlataforma);
                proyecto.get().setCartaResponsivaStorage(cartaResponsivaStorage);
                proyecto.get().setCartaResponsivaHa(cartaResponsivaHa);
                proyecto.get().setCartaResponsivaGsoc(cartaResponsivaGsoc);
                proyecto.get().setCartaResponsivaLlaves(cartaResponsivaLlaves);
                proyecto.get().setOtros(otros);

                proyectos.add(proyecto.get());

                response.getProyectoResponse().setProyectos(proyectos);
                response.setMetadata("Respuesta exitosa", "00", "¡Proyecto encontrado exitosamente");

            } else {
                response.setMetadata("Respuesta fallida", "-1", "¡Proyecto no encontrado!");
                return new ResponseEntity<ProyectoResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            ex.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "¡Error al buscar el proyecto por su id!");
            return new ResponseEntity<ProyectoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<ProyectoResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la busqueda de un registro por su atributo nombre
     * @param nombre String paramentro del nombre del proyecto a buscar
     * @return ResponseEntity del tipo ProyectoResponseRest
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProyectoResponseRest> findByNombre(String nombre) {

        ProyectoResponseRest response = new ProyectoResponseRest();
        List<Proyecto> proyectos = new ArrayList<>();
        List<Proyecto> proyectoNombre = new ArrayList<>();

        try {
            // Buscar el proyecto por su nombre
            proyectoNombre = this.proyectoRepository.findByNombreLike(nombre);

            if (proyectoNombre.size() > 0) {
                proyectoNombre.stream().forEach((proyecto) -> {
                    String f60 = proyecto.getF60();
                    String lld = proyecto.getLld();
                    String hld = proyecto.getHld();
                    String memoriaTecnica = proyecto.getMemoriaTecnica();
                    String layout = proyecto.getLayout();
                    String sla = proyecto.getSla();
                    String reporteFotografico = proyecto.getReporteFotografico();
                    String asignacionFuerzaEspacio = proyecto.getAsignacionFuerzaEspacio();
                    String etiquetado = proyecto.getEtiquetado();
                    String planos = proyecto.getPlanos();
                    String proyectoEjecutivo = proyecto.getProyectoEjecutivo();
                    String inventarioHardware = proyecto.getInventarioHardware();
                    String nodos = proyecto.getNodos();
                    String atpFisico = proyecto.getAtpFisico();
                    String atpFisicoFirmado = proyecto.getAtpFisicoFirmado();
                    String atpLogico = proyecto.getAtpLogico();
                    String reporteTransferenciaOperativa = proyecto.getReporteTransferenciaOperativa();
                    String cartaResponsivaIaaS = proyecto.getCartaResponsivaIaaS();
                    String cartaResponsivaPlataforma = proyecto.getCartaResponsivaPlataforma();
                    String cartaResponsivaStorage = proyecto.getCartaResponsivaStorage();
                    String cartaResponsivaHa = proyecto.getCartaResponsivaHa();
                    String cartaResponsivaGsoc = proyecto.getCartaResponsivaGsoc();
                    String cartaResponsivaLlaves = proyecto.getCartaResponsivaLlaves();
                    String otros = proyecto.getOtros();

                    proyecto.setF60(f60);
                    proyecto.setLld(lld);
                    proyecto.setHld(hld);
                    proyecto.setMemoriaTecnica(memoriaTecnica);
                    proyecto.setLayout(layout);
                    proyecto.setSla(sla);
                    proyecto.setReporteFotografico(reporteFotografico);
                    proyecto.setAsignacionFuerzaEspacio(asignacionFuerzaEspacio);
                    proyecto.setEtiquetado(etiquetado);
                    proyecto.setPlanos(planos);
                    proyecto.setProyectoEjecutivo(proyectoEjecutivo);
                    proyecto.setInventarioHardware(inventarioHardware);
                    proyecto.setNodos(nodos);
                    proyecto.setAtpFisico(atpFisico);
                    proyecto.setAtpFisicoFirmado(atpFisicoFirmado);
                    proyecto.setAtpLogico(atpLogico);
                    proyecto.setReporteTransferenciaOperativa(reporteTransferenciaOperativa);
                    proyecto.setCartaResponsivaIaaS(cartaResponsivaIaaS);
                    proyecto.setCartaResponsivaPlataforma(cartaResponsivaPlataforma);
                    proyecto.setCartaResponsivaStorage(cartaResponsivaStorage);
                    proyecto.setCartaResponsivaHa(cartaResponsivaHa);
                    proyecto.setCartaResponsivaGsoc(cartaResponsivaGsoc);
                    proyecto.setCartaResponsivaLlaves(cartaResponsivaLlaves);
                    proyecto.setOtros(otros);

                    proyectos.add(proyecto);
                });
                response.getProyectoResponse().setProyectos(proyectos);
                response.setMetadata("Respuesta exitosa", "00", "¡Producto encontrado exitosamente!");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "¡Proyecto no encontrado por el nombre!");
                return new ResponseEntity<ProyectoResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            ex.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "¡Error al buscar el proyecto por su nombre");
            return new ResponseEntity<ProyectoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProyectoResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la persistencia de datos en la base de datos
     * @param proyecto objeto de tipo Proyecto que sera persistido
     * @param responsableId identificador unico para persistir el responsable del proyecto
     * @return ResponseEntity con todos los objetos de tipo Proyecto
     */
    @Override
   @Transactional
    public ResponseEntity<ProyectoResponseRest> save(Proyecto proyecto, Long responsableId, Long tipoProyectoId, Long sitioId, Long clienteId, String fechaInicio,String fechaLiberacion, MultipartFile fileF60,
                                                     MultipartFile fileLld, MultipartFile fileHld, MultipartFile fileMemoriaTecnica, MultipartFile fileSid, MultipartFile fileLayout, MultipartFile fileSla, MultipartFile filePresentacion,
                                                     MultipartFile fileReporteFotografico, MultipartFile fileAsignacionFuerzaEspacio, MultipartFile fileEtiquetado, MultipartFile filePlanos, MultipartFile fileProyectoEjecutivo,
                                                     MultipartFile fileInventarioHardware, MultipartFile fileAtpFisico, MultipartFile fileAtpFisicoFirmado,
                                                     MultipartFile fileAtpLogico, MultipartFile fileReporteTransferenciaOperativa,
                                                     MultipartFile fileCartaResponsivaIaaS, MultipartFile fileCartaResponsivaPlataforma,
                                                     MultipartFile fileCartaResponsivaStorage, MultipartFile fileCartaResponsivaHa, MultipartFile fileCartaResponsivaGsoc, MultipartFile fileCartaResponsivaLlaves,
                                                     MultipartFile fileOtros, MultipartFile[] archivosAdjuntos, String username) {

        ProyectoResponseRest response = new ProyectoResponseRest();
        List<Proyecto> proyectos = new ArrayList<>();

        try {
            // Persistimos sólo datos básicos del proyecto
            Optional<Responsable> responsable = this.responsableRepository.findById(responsableId);
            Optional<TipoProyecto> tipoProyecto = this.tipoProyectoRepository.findById(tipoProyectoId);
            Optional<Sitio> sitio = this.sitioRepository.findById(sitioId);
            Optional<Cliente> cliente = this.clienteRepository.findById(clienteId);

            if (!responsable.isPresent()) {
                response.setMetadata("Respuesta no exitosa", "-1", "Responsable no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (!tipoProyecto.isPresent()) {
                response.setMetadata("Respuesta no exitosa", "-1", "Tipo de proyecto no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (!sitio.isPresent()) {
                response.setMetadata("Respuesta no exitosa", "-1", "Sitio no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (!cliente.isPresent()) {
                response.setMetadata("Respuesta no exitosa", "-1", "Cliente no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            proyecto.setResponsableProyecto(responsable.get());
            proyecto.setTipoProyecto(tipoProyecto.get());
            proyecto.setSitio(sitio.get());
            proyecto.setCliente(cliente.get());
            proyecto.setNombre(proyecto.getNombre().toUpperCase());
            proyecto.setFechaLiberacion(fechaLiberacion);
            proyecto.setFechaInicio(fechaInicio);

            Proyecto persistido = this.proyectoRepository.save(proyecto);

            // Logica para subir archivos masivos
            if (archivosAdjuntos != null && archivosAdjuntos.length > 0) {
                try {
                    // 1. Llamada física (Pasamos solo los 2 argumentos que espera el método)
                    List<Map<String, String>> adjuntosGuardados = uploadFileService.guardarAdjuntosMasivosFisicos(persistido.getNombre(), archivosAdjuntos);

                    // 2. Lógica de la Entity: Guardamos cada uno en la BD
                    for (Map<String, String> datos : adjuntosGuardados) {
                        DocumentoAdjunto documentoAdjunto = new DocumentoAdjunto();
                        documentoAdjunto.setNombreArchivo(datos.get("nombre"));
                        documentoAdjunto.setRutaArchivo(datos.get("ruta"));
                        documentoAdjunto.setUsuarioSubio(username); // Usamos el username aquí en el Service de Proyectos
                        documentoAdjunto.setProyecto(persistido);

                        this.adjuntoRepository.save(documentoAdjunto);
                    }
                } catch (IOException e) {
                    logger.error("Error al procesar adjuntos: {}", e.getMessage());
                }
            }

            // Registro de la bitacora
            Bitacora bitacora = new Bitacora();
            bitacora.setAccion("CREATE");
            bitacora.setUsuario(username);
            bitacora.setDetalle("Creó el proyecto: " + persistido.getNombre() + " con ID: " + persistido.getIdProyecto());
            this.bitacoraRepository.save(bitacora);

            // Helper local para subir los archivos
            BiFunction<MultipartFile, String, String> guardarODefault = (mpf, defecto) -> {
                if (mpf != null && !mpf.isEmpty()) {
                    try {
                        return uploadFileService.copiarArchivoEnSubCarpeta(persistido.getNombre(), mpf, false);
                    } catch (IOException e) {
                        logger.error("Error al copiar {} en subcarpeta {}: {}",
                                mpf.getOriginalFilename(),
                                persistido.getNombre(),
                                e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                } else {
                    // se podra usar el valor antiguamente guardado, si se prefiere:
                    return defecto != null ? defecto : "NA";
                }
            };

            // Seteamos el nombre del archivo o la palabra pendiente en la base de datos
            persistido.setF60(guardarODefault.apply(fileF60, proyecto.getF60()));
            persistido.setLld(guardarODefault.apply(fileLld, proyecto.getLld()));
            persistido.setHld(guardarODefault.apply(fileHld, proyecto.getHld()));
            persistido.setMemoriaTecnica(guardarODefault.apply(fileMemoriaTecnica, persistido.getMemoriaTecnica()));
            persistido.setSid(guardarODefault.apply(fileSid, persistido.getSid()));
            persistido.setLayout(guardarODefault.apply(fileLayout, proyecto.getLayout()));
            persistido.setSla(guardarODefault.apply(fileSla, proyecto.getSla()));
            persistido.setPresentacion(guardarODefault.apply(filePresentacion, proyecto.getPresentacion()));
            persistido.setReporteFotografico(guardarODefault.apply(fileReporteFotografico, proyecto.getReporteFotografico()));
            persistido.setAsignacionFuerzaEspacio(guardarODefault.apply(fileAsignacionFuerzaEspacio, proyecto.getAsignacionFuerzaEspacio()));
            persistido.setEtiquetado(guardarODefault.apply(fileEtiquetado, persistido.getEtiquetado()));
            persistido.setPlanos(guardarODefault.apply(filePlanos, persistido.getPlanos()));
            persistido.setProyectoEjecutivo(guardarODefault.apply(fileProyectoEjecutivo, persistido.getProyectoEjecutivo()));
            persistido.setInventarioHardware(guardarODefault.apply(fileInventarioHardware, proyecto.getInventarioHardware()));
            persistido.setAtpFisico(guardarODefault.apply(fileAtpFisico, proyecto.getAtpFisico()));
            persistido.setAtpFisicoFirmado(guardarODefault.apply(fileAtpFisicoFirmado, proyecto.getAtpFisicoFirmado()));
            persistido.setAtpLogico(guardarODefault.apply(fileAtpLogico, proyecto.getAtpLogico()));
            persistido.setReporteTransferenciaOperativa(guardarODefault.apply(fileReporteTransferenciaOperativa, proyecto.getReporteTransferenciaOperativa()));
            persistido.setCartaResponsivaIaaS(guardarODefault.apply(fileCartaResponsivaIaaS, proyecto.getCartaResponsivaIaaS()));
            persistido.setCartaResponsivaPlataforma(guardarODefault.apply(fileCartaResponsivaPlataforma, proyecto.getCartaResponsivaPlataforma()));
            persistido.setCartaResponsivaStorage(guardarODefault.apply(fileCartaResponsivaStorage, proyecto.getCartaResponsivaStorage()));
            persistido.setCartaResponsivaHa(guardarODefault.apply(fileCartaResponsivaHa, proyecto.getCartaResponsivaHa()));
            persistido.setCartaResponsivaGsoc(guardarODefault.apply(fileCartaResponsivaGsoc, proyecto.getCartaResponsivaGsoc()));
            persistido.setCartaResponsivaLlaves(guardarODefault.apply(fileCartaResponsivaLlaves, proyecto.getCartaResponsivaLlaves()));
            persistido.setOtros(guardarODefault.apply(fileOtros, proyecto.getOtros()));

            // Actualiza la entidad con las rutas finales
            Proyecto actualizado = this.proyectoRepository.save(persistido);

            if (actualizado != null) {
                proyectos.add(actualizado);
                response.getProyectoResponse().setProyectos(proyectos);
                response.setMetadata("Respuesta exitosa", "00", "Proyecto guardado con éxito");
            } else {
                response.setMetadata("Respuesta no exitosa", "-1", "Proyecto no guardado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception ex) {
            ex.getStackTrace();
            response.setMetadata("Respuesta no exitosa", "-1", "Error al persistir el proyecto en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param proyecto      objeto de tipo Proyecto que sera el actualizado mediante su identificador unico
     * @param responsableId objeto de tipo ResponsableProyecto que se actualizara mediante su identificador unico
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<ProyectoResponseRest> update(Proyecto proyecto, Long responsableId, Long tipoProyectoId, Long sitioId, Long clienteId, String fechaInicio,String fechaLiberacion, MultipartFile fileF60,
                                                       MultipartFile fileLld, MultipartFile fileHld, MultipartFile fileMemoriaTecnica, MultipartFile fileSid, MultipartFile fileLayout, MultipartFile fileSla, MultipartFile filePresentacion,
                                                       MultipartFile fileReporteFotografico, MultipartFile fileAsignacionFuerzaEspacio, MultipartFile fileEtiquetado, MultipartFile filePlanos, MultipartFile fileProyectoEjecutivo,
                                                       MultipartFile fileInventarioHardware, MultipartFile fileAtpFisico, MultipartFile fileAtpFisicoFirmado,
                                                       MultipartFile fileAtpLogico, MultipartFile fileReporteTransferenciaOperativa,
                                                       MultipartFile fileCartaResponsivaIaaS, MultipartFile fileCartaResponsivaPlataforma,
                                                       MultipartFile fileCartaResponsivaStorage, MultipartFile fileCartaResponsivaHa, MultipartFile fileCartaResponsivaGsoc, MultipartFile fileCartaResponsivaLlaves,
                                                       MultipartFile fileOtros, MultipartFile[] archivosAdjuntos, String username) {

        ProyectoResponseRest response = new ProyectoResponseRest();

        response.setMetaList(new ArrayList<>());
        response.setProyectoResponse(new ProyectoResponse());

        try {
            // Recuperamos el proyecto existente o lanzamos excepcion si no existe en la base de datos
            Proyecto proyectoActualizado = proyectoRepository.findById(proyecto.getIdProyecto()).orElseThrow(() ->
                    new EntityNotFoundException("Proyecto con id " + proyecto.getIdProyecto() + " no existe"));

            // Renombrado de la carpeta anterior
            String nombreAnterior = proyectoActualizado.getNombre().trim().replaceAll("[\\\\/:*?\"<>| ]+", "_").toUpperCase();
            String nombreNuevo = proyecto.getNombre().trim().replaceAll("[\\\\/:*?\"<>| ]+", "_").toUpperCase();

            if (!nombreAnterior.equals(nombreNuevo)) {
                Path rutaAntigua = Paths.get(documentacionProyectos, nombreAnterior);
                Path rutaNueva = Paths.get(documentacionProyectos, nombreNuevo);

                try {
                    if (Files.exists(rutaAntigua)) {
                        Files.move(rutaAntigua, rutaNueva, StandardCopyOption.REPLACE_EXISTING);
                        logger.info("✔ Carpeta renombrada físicamente de {} a {}",  nombreAnterior, nombreNuevo);
                        actualizarRutasPorRenombrado(proyectoActualizado, nombreAnterior, nombreNuevo);
                    }

                } catch (IOException ex) {
                    logger.error("✖ Error crítico al renombrar carpeta: {}", ex.getMessage());
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo renombrar el directorio de archivos");
                }
            }

            // Actualizamos los campos basicos (Strings)
            proyectoActualizado.setNombre(proyecto.getNombre());
            proyectoActualizado.setFechaLiberacion(fechaLiberacion);
            proyectoActualizado.setFechaInicio(fechaInicio);
            proyectoActualizado.setNodos(proyecto.getNodos());
            Optional<Responsable> responsable = this.responsableRepository.findById(responsableId);
            Optional<TipoProyecto> tipoProyecto = this.tipoProyectoRepository.findById(tipoProyectoId);
            Optional<Sitio> sitio = this.sitioRepository.findById(sitioId);
            Optional<Cliente> cliente = this.clienteRepository.findById(clienteId);

            if (!responsable.isPresent()) {
                response.setMetadata("Respuesta no exitosa", "-1", "Responsable no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (!tipoProyecto.isPresent()) {
                response.setMetadata("Respuesta no exitosa", "-1", "Responsable no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (!sitio.isPresent()) {
                response.setMetadata("Respuesta no exitosa", "-1", "Sitio no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (!cliente.isPresent()) {
                response.setMetadata("Respuesta no exitosa", "-1", "Cliente no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            proyectoActualizado.setResponsableProyecto(responsable.get());
            proyectoActualizado.setTipoProyecto(tipoProyecto.get());
            proyectoActualizado.setSitio(sitio.get());
            proyectoActualizado.setCliente(cliente.get());

            // Helper local para subir los archivos
            BiFunction<MultipartFile, String, String> guardarODefault = (mpf, defecto) -> {
                if (mpf != null && !mpf.isEmpty()) {
                    try {
                        if (defecto != null && !defecto.trim().equalsIgnoreCase("NA")) {

                            Path pathBase = Paths.get(documentacionProyectos);
                            Path rutaRelativa = Paths.get(defecto);

                            Path rutaCompletaArchivo = pathBase.resolve(rutaRelativa).toAbsolutePath().normalize();

                            logger.info("Intentando eliminar archivo viejo en:: {}", rutaCompletaArchivo);

                            if (Files.exists(rutaCompletaArchivo)) {
                                Files.delete(rutaCompletaArchivo);
                                logger.info("🗑️ Limpieza: Archivo anterior eliminado físicamente: {}", rutaCompletaArchivo.getFileName());
                            } else  {
                                // Intento de respaldo: Probar ruta que viene de la base de datos
                                Path rutaDirecta = Paths.get(defecto).toAbsolutePath().normalize();
                                if (Files.exists(rutaDirecta)) {
                                    Files.delete(rutaDirecta);
                                    logger.info("🗑️ Limpieza (Ruta Directa): Archivo eliminado.");
                                } else {
                                    logger.warn("No se pudo encontrar el archivo físico para borrar ninguna de las rutas.");
                                }
                            }
                        }
                        // Subida normal despues de borrar
                        return uploadFileService.copiarArchivoEnSubCarpeta(proyectoActualizado.getNombre(), mpf, true);

                    } catch (IOException e) {
                        logger.error("Error al copiar {} en subcarpeta {}: {}",
                                mpf.getOriginalFilename(),
                                proyectoActualizado.getNombre(),
                                e.getMessage(), e);
                        throw new RuntimeException("Error en el reemplazo físico del archivo", e);
                    }
                } else {
                    // se podra usar el valor antiguamente guardado, si se prefiere:
                    return defecto;
                }
            };

            // Actualizamos los archivos
            proyectoActualizado.setF60(guardarODefault.apply(fileF60, proyectoActualizado.getF60()));
            proyectoActualizado.setLld(guardarODefault.apply(fileLld, proyectoActualizado.getLld()));
            proyectoActualizado.setHld(guardarODefault.apply(fileHld, proyectoActualizado.getHld()));
            proyectoActualizado.setMemoriaTecnica(guardarODefault.apply(fileMemoriaTecnica, proyectoActualizado.getMemoriaTecnica()));
            proyectoActualizado.setSid(guardarODefault.apply(fileSid, proyectoActualizado.getSid()));
            proyectoActualizado.setLayout(guardarODefault.apply(fileLayout, proyectoActualizado.getLayout()));
            proyectoActualizado.setSla(guardarODefault.apply(fileSla, proyectoActualizado.getSla()));
            proyectoActualizado.setPresentacion(guardarODefault.apply(filePresentacion, proyectoActualizado.getPresentacion()));
            proyectoActualizado.setReporteFotografico(guardarODefault.apply(fileReporteFotografico, proyectoActualizado.getReporteFotografico()));
            proyectoActualizado.setAsignacionFuerzaEspacio(guardarODefault.apply(fileAsignacionFuerzaEspacio, proyectoActualizado.getAsignacionFuerzaEspacio()));
            proyectoActualizado.setEtiquetado(guardarODefault.apply(fileEtiquetado, proyectoActualizado.getEtiquetado()));
            proyectoActualizado.setPlanos(guardarODefault.apply(filePlanos, proyectoActualizado.getPlanos()));
            proyectoActualizado.setProyectoEjecutivo(guardarODefault.apply(fileProyectoEjecutivo, proyectoActualizado.getProyectoEjecutivo()));
            proyectoActualizado.setInventarioHardware(guardarODefault.apply(fileInventarioHardware, proyectoActualizado.getInventarioHardware()));
            proyectoActualizado.setAtpFisico(guardarODefault.apply(fileAtpFisico, proyectoActualizado.getAtpFisico()));
            proyectoActualizado.setAtpFisicoFirmado(guardarODefault.apply(fileAtpFisicoFirmado, proyectoActualizado.getAtpFisicoFirmado()));
            proyectoActualizado.setAtpLogico(guardarODefault.apply(fileAtpLogico, proyectoActualizado.getAtpLogico()));
            proyectoActualizado.setReporteTransferenciaOperativa(guardarODefault.apply(fileReporteTransferenciaOperativa, proyectoActualizado.getReporteTransferenciaOperativa()));
            proyectoActualizado.setCartaResponsivaIaaS(guardarODefault.apply(fileCartaResponsivaIaaS, proyectoActualizado.getCartaResponsivaIaaS()));
            proyectoActualizado.setCartaResponsivaPlataforma(guardarODefault.apply(fileCartaResponsivaPlataforma, proyectoActualizado.getCartaResponsivaPlataforma()));
            proyectoActualizado.setCartaResponsivaStorage(guardarODefault.apply(fileCartaResponsivaStorage, proyectoActualizado.getCartaResponsivaStorage()));
            proyectoActualizado.setCartaResponsivaHa(guardarODefault.apply(fileCartaResponsivaHa, proyectoActualizado.getCartaResponsivaHa()));
            proyectoActualizado.setCartaResponsivaGsoc(guardarODefault.apply(fileCartaResponsivaGsoc, proyectoActualizado.getCartaResponsivaGsoc()));
            proyectoActualizado.setCartaResponsivaLlaves(guardarODefault.apply(fileCartaResponsivaLlaves, proyectoActualizado.getCartaResponsivaLlaves()));
            proyectoActualizado.setOtros(guardarODefault.apply(fileOtros, proyectoActualizado.getOtros()));

            // Registro de la bitacora
            Bitacora bitacora = new Bitacora();
            bitacora.setAccion("UPDATE");
            bitacora.setUsuario(username);
            bitacora.setDetalle("Actualizó datos/archivos del proyecto " + proyectoActualizado.getNombre() + " con el ID " + proyectoActualizado.getIdProyecto());
            this.bitacoraRepository.save(bitacora);

            // Persistir en la base de datos
            proyectoRepository.save(proyectoActualizado);

            // Logica para subir archivos masivos
            if (archivosAdjuntos != null && archivosAdjuntos.length > 0) {
                try {
                    // 1. Llamada física (Pasamos solo los 2 argumentos que espera el método)
                    List<Map<String, String>> adjuntosGuardados = uploadFileService.guardarAdjuntosMasivosFisicos(proyectoActualizado.getNombre(), archivosAdjuntos);

                    // 2. Lógica de la Entity: Guardamos cada uno en la BD
                    for (Map<String, String> datos : adjuntosGuardados) {

                        String nombreArchivo = datos.get("nombre");

                        Optional<DocumentoAdjunto> existente = adjuntoRepository.findByNombreArchivoAndProyecto_IdProyecto(nombreArchivo, proyectoActualizado.getIdProyecto());

                        if (existente.isPresent()) {
                            DocumentoAdjunto doc = existente.get();
                            doc.setUsuarioSubio(username);
                            doc.setRutaArchivo(datos.get("ruta"));
                            this.adjuntoRepository.save(doc);
                            logger.info("✔ Registro actualizado en BD para el archivo: {}", nombreArchivo);
                        } else {
                            DocumentoAdjunto documentoAdjunto = new DocumentoAdjunto();
                            documentoAdjunto.setNombreArchivo(datos.get("nombre"));
                            documentoAdjunto.setRutaArchivo(datos.get("ruta"));
                            documentoAdjunto.setUsuarioSubio(username); // Usamos el username aquí en el Service de Proyectos
                            documentoAdjunto.setProyecto(proyectoActualizado);

                            this.adjuntoRepository.save(documentoAdjunto);
                            logger.info("➕ Nuevo registro creado en BD para el archivo: {}", nombreArchivo);
                        }
                    }
                } catch (IOException e) {
                    logger.error("Error al procesar adjuntos: {}", e.getMessage());
                }
            }

            // Se arma el response
            response.getMetaList().add(Map.of("code", "00", "message", "¡Se ha actualizado el proyecto exitosamente"));
            response.getProyectoResponse().setProyectos(List.of(proyectoActualizado));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("¡Error al actualizar el proyecto: ", e);
            response.getMetaList().add(Map.of("code", "-1", "message", "¡Error al actualizar el proyecto"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Metodo auxiliar para el renombrado de rutas por actualización de nombre de proyecto y carpeta física
     */
    private void actualizarRutasPorRenombrado (Proyecto proyecto, String viejo, String nuevo) {
        if (proyecto.getF60() != null) proyecto.setF60(proyecto.getF60().replace(viejo, nuevo));
        if (proyecto.getLld() != null) proyecto.setLld(proyecto.getLld().replace(viejo, nuevo));
        if (proyecto.getHld() != null) proyecto.setHld(proyecto.getHld().replace(viejo, nuevo));
        if (proyecto.getMemoriaTecnica() != null) proyecto.setMemoriaTecnica(proyecto.getMemoriaTecnica().replace(viejo, nuevo));
        if (proyecto.getSid() != null) proyecto.setSid(proyecto.getSid().replace(viejo, nuevo));
        if (proyecto.getLayout() != null) proyecto.setLayout(proyecto.getLayout().replace(viejo, nuevo));
        if (proyecto.getPresentacion() != null) proyecto.setPresentacion(proyecto.getPresentacion().replace(viejo, nuevo));
        if (proyecto.getSla() != null) proyecto.setSla(proyecto.getSla().replace(viejo, nuevo));
        if (proyecto.getReporteFotografico() != null) proyecto.setReporteFotografico(proyecto.getReporteFotografico().replace(viejo, nuevo));
        if (proyecto.getAsignacionFuerzaEspacio() != null) proyecto.setAsignacionFuerzaEspacio(proyecto.getAsignacionFuerzaEspacio().replace(viejo, nuevo));
        if (proyecto.getEtiquetado() != null) proyecto.setEtiquetado(proyecto.getEtiquetado().replace(viejo, nuevo));
        if (proyecto.getPlanos() != null) proyecto.setPlanos(proyecto.getPlanos().replace(viejo, nuevo));
        if (proyecto.getProyectoEjecutivo() != null) proyecto.setProyectoEjecutivo(proyecto.getProyectoEjecutivo().replace(viejo, nuevo));
        if (proyecto.getInventarioHardware() != null) proyecto.setInventarioHardware(proyecto.getInventarioHardware().replace(viejo, nuevo));
        if (proyecto.getAtpFisico() != null) proyecto.setAtpFisico(proyecto.getAtpFisico().replace(viejo, nuevo));
        if (proyecto.getAtpFisicoFirmado() != null) proyecto.setAtpFisicoFirmado(proyecto.getAtpFisicoFirmado().replace(viejo, nuevo));
        if (proyecto.getAtpLogico() != null) proyecto.setAtpLogico(proyecto.getAtpLogico().replace(viejo, nuevo));
        if (proyecto.getReporteTransferenciaOperativa() != null) proyecto.setReporteTransferenciaOperativa(proyecto.getReporteTransferenciaOperativa().replace(viejo, nuevo));
        if (proyecto.getCartaResponsivaIaaS() != null) proyecto.setCartaResponsivaIaaS(proyecto.getCartaResponsivaIaaS().replace(viejo, nuevo));
        if (proyecto.getCartaResponsivaPlataforma() != null) proyecto.setCartaResponsivaPlataforma(proyecto.getCartaResponsivaPlataforma().replace(viejo, nuevo));
        if (proyecto.getCartaResponsivaStorage() != null) proyecto.setCartaResponsivaStorage(proyecto.getCartaResponsivaStorage().replace(viejo, nuevo));
        if (proyecto.getCartaResponsivaHa() != null) proyecto.setCartaResponsivaHa(proyecto.getCartaResponsivaHa().replace(viejo, nuevo));
        if (proyecto.getCartaResponsivaGsoc() != null) proyecto.setCartaResponsivaGsoc(proyecto.getCartaResponsivaGsoc().replace(viejo, nuevo));
        if (proyecto.getCartaResponsivaLlaves() != null) proyecto.setCartaResponsivaLlaves(proyecto.getCartaResponsivaLlaves().replace(viejo, nuevo));
        if (proyecto.getOtros() != null) proyecto.setOtros(proyecto.getOtros().replace(viejo, nuevo));

        // Renombrado de los documentos adicionales en referencias en base de datos
        List<DocumentoAdjunto> adjuntos = proyecto.getAdjuntos();
        if (adjuntos != null && !adjuntos.isEmpty()) {
            for (DocumentoAdjunto adj : adjuntos) {
                if (adj.getRutaArchivo() != null) {
                    String nuevaRuta = adj.getRutaArchivo().replace(viejo, nuevo);
                    adj.setRutaArchivo(nuevaRuta);
                    logger.info("✔ Ruta de adjunto actualizada: {}", adj.getNombreArchivo());
                }
            }
        }
    }

    /**
     * Metodo que realiza el borrado del proyecto a traves de su identificador unico
     * @param idProyecto identificador unico del proyecto
     * @return ResponseEntity de objeto de tipo ProyectoResponseRest
     */
    @Override
    @Transactional
    public void deleteProyecto(Long idProyecto, String username) {

        // Obtengo el proyecto o se lanza excepcion si no existe
        Proyecto proyecto = proyectoRepository.findById(idProyecto).orElseThrow(() ->
                new RuntimeException("No existe el proyecto con id " + idProyecto));

        // Obtenemos el año del proyecto
        int anioProyecto = proyecto.getAnio();

        // Obtenemos el año en curso
        int anioActual = LocalDate.now().getYear();

        // Restriccion
        if (anioProyecto != anioActual) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sólo se pueden eliminar proyectos del año " + anioActual);
        }

        // Sanitizamos la carpeta con el nombre real en disco con _
        String carpeta = proyecto.getNombre().trim().replaceAll("[\\\\/:*?\"<>| ]+", "_").toUpperCase();

        // Borramos la carpeta de archivos
        Path proyectoCarpeta = Paths.get(documentacionProyectos, carpeta);
        logger.info("-> Intentando borrar carpeta de proyecto: {}" , proyectoCarpeta.toAbsolutePath());

        try {
            if (Files.exists(proyectoCarpeta)) {
                FileUtils.deleteDirectory(proyectoCarpeta.toFile());
                logger.info("   ✔ Carpeta borrada correctamente: {}", proyectoCarpeta);
            } else {
                logger.warn("   ⚠ La carpeta no existe, salteando borrado: {}", proyectoCarpeta);
            }
        } catch (IOException e) {
            logger.error("   ✖ Error borrando archivos del proyecto {}: {}", proyecto.getNombre(), e.getMessage(), e);
            throw new RuntimeException("Error borrando archivos del proyecto " + proyecto.getNombre(), e);
        }

        try {
            // Registro en bitácora antes de borrar de la BD
            Bitacora bitacora = new Bitacora();
            bitacora.setAccion("DELETE");
            bitacora.setUsuario(username);
            bitacora.setDetalle("Eliminó el proyecto: " + proyecto.getNombre() + " con el ID: " + idProyecto);
            this.bitacoraRepository.save(bitacora);

            // Borrado de la entidad en la base de datos
            proyectoRepository.deleteById(idProyecto);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar el departamento porque hay responsables asociados");
        }
    }
}
