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
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * Objeto de tipo IRegionRepository con el CRUD respectivo
     */
    @Autowired
    private IRegionRepository regionRepository;

    /**
     * Objeto de tipo ISitioRepository con el CRUD respectivo
     */
    @Autowired
    private ISitioRepository sitioRepository;

    @Autowired
    private ISubirArchivoService uploadFileService;

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
                    String layout = proyecto.getLayout();
                    String sla = proyecto.getSla();
                    String reporteFotografico = proyecto.getReporteFotografico();
                    String asignacionFuerzaEspacio = proyecto.getAsignacionFuerzaEspacio();
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

                    proyecto.setF60(f60);
                    proyecto.setLld(lld);
                    proyecto.setHld(hld);
                    proyecto.setLayout(layout);
                    proyecto.setSla(sla);
                    proyecto.setReporteFotografico(reporteFotografico);
                    proyecto.setAsignacionFuerzaEspacio(asignacionFuerzaEspacio);
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
                String layout = proyecto.get().getLayout();
                String sla = proyecto.get().getSla();
                String reporteFotografico = proyecto.get().getReporteFotografico();
                String asignacionFuerzaEspacio = proyecto.get().getAsignacionFuerzaEspacio();
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

                proyecto.get().setF60(f60);
                proyecto.get().setLld(lld);
                proyecto.get().setHld(hld);
                proyecto.get().setLayout(layout);
                proyecto.get().setSla(sla);
                proyecto.get().setReporteFotografico(reporteFotografico);
                proyecto.get().setAsignacionFuerzaEspacio(asignacionFuerzaEspacio);
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
                    String layout = proyecto.getLayout();
                    String sla = proyecto.getSla();
                    String reporteFotografico = proyecto.getReporteFotografico();
                    String asignacionFuerzaEspacio = proyecto.getAsignacionFuerzaEspacio();
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

                    proyecto.setF60(f60);
                    proyecto.setLld(lld);
                    proyecto.setHld(hld);
                    proyecto.setLayout(layout);
                    proyecto.setSla(sla);
                    proyecto.setReporteFotografico(reporteFotografico);
                    proyecto.setAsignacionFuerzaEspacio(asignacionFuerzaEspacio);
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
    public ResponseEntity<ProyectoResponseRest> save(Proyecto proyecto, Long responsableId, Long tipoProyectoId, Long sitioId, String fechaInicio,String fechaLiberacion, MultipartFile fileF60,
                                                     MultipartFile fileLld, MultipartFile fileHld, MultipartFile fileLayout, MultipartFile fileSla, MultipartFile filePresentacion,
                                                     MultipartFile fileReporteFotografico, MultipartFile fileAsignacionFuerzaEspacio,
                                                     MultipartFile fileInventarioHardware, MultipartFile fileAtpFisico, MultipartFile fileAtpFisicoFirmado,
                                                     MultipartFile fileAtpLogico, MultipartFile fileReporteTransferenciaOperativa,
                                                     MultipartFile fileCartaResponsivaIaaS, MultipartFile fileCartaResponsivaPlataforma,
                                                     MultipartFile fileCartaResponsivaStorage, MultipartFile fileCartaResponsivaHa, MultipartFile fileCartaResponsivaGsoc,
                                                     MultipartFile fileOtros) {

        ProyectoResponseRest response = new ProyectoResponseRest();
        List<Proyecto> proyectos = new ArrayList<>();

        try {
            // Persistimos sólo datos básicos del proyecto
            Optional<Responsable> responsable = this.responsableRepository.findById(responsableId);
            Optional<TipoProyecto> tipoProyecto = this.tipoProyectoRepository.findById(tipoProyectoId);
            Optional<Sitio> sitio = this.sitioRepository.findById(sitioId);

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

            proyecto.setResponsableProyecto(responsable.get());
            proyecto.setTipoProyecto(tipoProyecto.get());
            proyecto.setSitio(sitio.get());
            proyecto.setNombre(proyecto.getNombre().toUpperCase());
            proyecto.setFechaLiberacion(fechaLiberacion);
            proyecto.setFechaInicio(fechaInicio);

            Proyecto persistido = this.proyectoRepository.save(proyecto);

            // Elegimos como nombre de carpeta el nombre del proyecto (sanitizado por el servicio)
            String carpeta = persistido.getNombre();

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
            persistido.setLayout(guardarODefault.apply(fileLayout, proyecto.getLayout()));
            persistido.setSla(guardarODefault.apply(fileSla, proyecto.getSla()));
            persistido.setPresentacion(guardarODefault.apply(filePresentacion, proyecto.getPresentacion()));
            persistido.setReporteFotografico(guardarODefault.apply(fileReporteFotografico, proyecto.getReporteFotografico()));
            persistido.setAsignacionFuerzaEspacio(guardarODefault.apply(fileAsignacionFuerzaEspacio, proyecto.getAsignacionFuerzaEspacio()));
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
    public ResponseEntity<ProyectoResponseRest> update(Proyecto proyecto, Long responsableId, Long tipoProyectoId, Long sitioId, String fechaInicio,String fechaLiberacion, MultipartFile fileF60,
                                                       MultipartFile fileLld, MultipartFile fileHld, MultipartFile fileLayout, MultipartFile fileSla, MultipartFile filePresentacion,
                                                       MultipartFile fileReporteFotografico, MultipartFile fileAsignacionFuerzaEspacio,
                                                       MultipartFile fileInventarioHardware, MultipartFile fileAtpFisico, MultipartFile fileAtpFisicoFirmado,
                                                       MultipartFile fileAtpLogico, MultipartFile fileReporteTransferenciaOperativa,
                                                       MultipartFile fileCartaResponsivaIaaS, MultipartFile fileCartaResponsivaPlataforma,
                                                       MultipartFile fileCartaResponsivaStorage, MultipartFile fileCartaResponsivaHa, MultipartFile fileCartaResponsivaGsoc,
                                                       MultipartFile fileOtros) {

        ProyectoResponseRest response = new ProyectoResponseRest();

        response.setMetaList(new ArrayList<>());
        response.setProyectoResponse(new ProyectoResponse());

        try {
            // Recuperamos el proyecto existente o lanzamos excepcion si no existe en la base de datos
            Proyecto proyectoActualizado = proyectoRepository.findById(proyecto.getIdProyecto()).orElseThrow(() ->
                    new EntityNotFoundException("Proyecto con id " + proyecto.getIdProyecto() + " no existe"));

            // Actualizamos los campos basicos (Strings)
            proyectoActualizado.setNombre(proyecto.getNombre());
            proyectoActualizado.setFechaLiberacion(fechaLiberacion);
            proyectoActualizado.setFechaInicio(fechaInicio);
            proyectoActualizado.setNodos(proyecto.getNodos());
            Optional<Responsable> responsable = this.responsableRepository.findById(responsableId);
            Optional<TipoProyecto> tipoProyecto = this.tipoProyectoRepository.findById(tipoProyectoId);
            Optional<Sitio> sitio = this.sitioRepository.findById(sitioId);

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

            proyectoActualizado.setResponsableProyecto(responsable.get());
            proyectoActualizado.setTipoProyecto(tipoProyecto.get());
            proyectoActualizado.setSitio(sitio.get());

            // Helper local para subir los archivos
            BiFunction<MultipartFile, String, String> guardarODefault = (mpf, defecto) -> {
                if (mpf != null && !mpf.isEmpty()) {
                    try {
                        return uploadFileService.copiarArchivoEnSubCarpeta(
                                proyectoActualizado.getNombre(), mpf, true);
                    } catch (IOException e) {
                        logger.error("Error al copiar {} en subcarpeta {}: {}",
                                mpf.getOriginalFilename(),
                                proyectoActualizado.getNombre(),
                                e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                } else {
                    // se podra usar el valor antiguamente guardado, si se prefiere:
                    return defecto != null ? defecto : "NA";
                }
            };

            // Actualizamos los archivos
            proyectoActualizado.setF60(guardarODefault.apply(fileF60, proyectoActualizado.getF60()));
            proyectoActualizado.setLld(guardarODefault.apply(fileLld, proyectoActualizado.getLld()));
            proyectoActualizado.setHld(guardarODefault.apply(fileHld, proyectoActualizado.getHld()));
            proyectoActualizado.setLayout(guardarODefault.apply(fileLayout, proyectoActualizado.getLayout()));
            proyectoActualizado.setSla(guardarODefault.apply(fileSla, proyectoActualizado.getSla()));
            proyectoActualizado.setPresentacion(guardarODefault.apply(filePresentacion, proyecto.getPresentacion()));
            proyectoActualizado.setReporteFotografico(guardarODefault.apply(fileReporteFotografico, proyectoActualizado.getReporteFotografico()));
            proyectoActualizado.setAsignacionFuerzaEspacio(guardarODefault.apply(fileAsignacionFuerzaEspacio, proyectoActualizado.getAsignacionFuerzaEspacio()));
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
            proyectoActualizado.setOtros(guardarODefault.apply(fileOtros, proyecto.getOtros()));

            // Persistir en la base de datos
            proyectoRepository.save(proyectoActualizado);

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
     * Metodo que realiza el borrado del proyecto a traves de su identificador unico
     * @param idProyecto identificador unico del proyecto
     * @return ResponseEntity de objeto de tipo ProyectoResponseRest
     */
    @Override
    @Transactional
    public void deleteProyecto(Long idProyecto) {

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
            // Borrado de la entidad en la base de datos
            proyectoRepository.deleteById(idProyecto);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar el departamento porque hay responsables asociados");
        }
    }
}
