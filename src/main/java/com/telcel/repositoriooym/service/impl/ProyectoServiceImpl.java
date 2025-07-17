package com.telcel.repositoriooym.service.impl;

import com.telcel.repositoriooym.controller.ProyectoRestController;
import com.telcel.repositoriooym.entity.Proyecto;
import com.telcel.repositoriooym.entity.Responsable;
import com.telcel.repositoriooym.repository.IProyectoRepository;
import com.telcel.repositoriooym.repository.IResponsableRepository;
import com.telcel.repositoriooym.response.ProyectoResponse;
import com.telcel.repositoriooym.response.ProyectoResponseRest;
import com.telcel.repositoriooym.service.IProyectoService;
import com.telcel.repositoriooym.service.ISubirArchivoService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private ISubirArchivoService uploadFileService;

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
                    String atpLogicoFirmado = proyecto.getAtpLogicoFirmado();
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
                    proyecto.setAtpLogicoFirmado(atpLogicoFirmado);
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
                String atpLogicoFirmado = proyecto.get().getAtpLogicoFirmado();
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
                proyecto.get().setAtpLogicoFirmado(atpLogicoFirmado);
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
                    String atpLogicoFirmado = proyecto.getAtpLogicoFirmado();
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
                    proyecto.setAtpLogicoFirmado(atpLogicoFirmado);
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
    public ResponseEntity<ProyectoResponseRest> save(Proyecto proyecto, Long responsableId, String fechaLiberacion, MultipartFile fileF60,
                                                     MultipartFile fileLld, MultipartFile fileHld, MultipartFile fileLayout, MultipartFile fileSla,
                                                     MultipartFile fileReporteFotografico, MultipartFile fileAsignacionFuerzaEspacio,
                                                     MultipartFile fileInventarioHardware, MultipartFile fileAtpFisico, MultipartFile fileAtpFisicoFirmado,
                                                     MultipartFile fileAtpLogico, MultipartFile fileAtpLogicoFirmado, MultipartFile fileReporteTransferenciaOperativa,
                                                     MultipartFile fileCartaResponsivaIaaS, MultipartFile fileCartaResponsivaPlataforma,
                                                     MultipartFile fileCartaResponsivaStorage, MultipartFile fileCartaResponsivaHa, MultipartFile fileCartaResponsivaGsoc) {

        ProyectoResponseRest response = new ProyectoResponseRest();
        List<Proyecto> proyectos = new ArrayList<>();

        try {
            // Persistimos sólo datos básicos del proyecto
            Optional<Responsable> responsable = this.responsableRepository.findById(responsableId);

            if (!responsable.isPresent()) {
                response.setMetadata("Respuesta no exitosa", "-1", "Responsable no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            proyecto.setResponsableProyecto(responsable.get());
            proyecto.setNombre(proyecto.getNombre().toUpperCase());

            Proyecto guardado = this.proyectoRepository.save(proyecto);

            // Elegimos como nombre de carpeta el nombre del proyecto (sanitizado por el servicio)
            String carpeta = guardado.getNombre();

            // Copiamos cada archivo dentro de documentacion_proyectos/carpeta/
            guardado.setF60(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileF60));
            guardado.setLld(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileLld));
            guardado.setHld(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileHld));
            guardado.setLayout(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileLayout));
            guardado.setSla(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileSla));
            guardado.setReporteFotografico(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileReporteFotografico));
            guardado.setAsignacionFuerzaEspacio(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileAsignacionFuerzaEspacio));
            guardado.setInventarioHardware(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileInventarioHardware));
            guardado.setAtpFisico(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileAtpFisico));
            guardado.setAtpFisicoFirmado(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileAtpFisicoFirmado));
            guardado.setAtpLogico(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileAtpLogico));
            guardado.setAtpLogicoFirmado(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileAtpLogicoFirmado));
            guardado.setReporteTransferenciaOperativa(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileReporteTransferenciaOperativa));
            guardado.setCartaResponsivaIaaS(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileCartaResponsivaIaaS));
            guardado.setCartaResponsivaPlataforma(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileCartaResponsivaPlataforma));
            guardado.setCartaResponsivaStorage(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileCartaResponsivaStorage));
            guardado.setCartaResponsivaHa(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileCartaResponsivaHa));
            guardado.setCartaResponsivaGsoc(this.uploadFileService.copiarArchivoEnSubCarpeta(carpeta, fileCartaResponsivaGsoc));

            // Actualiza la entidad con las rutas finales
            Proyecto actualizado = this.proyectoRepository.save(guardado);

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
    public ResponseEntity<ProyectoResponseRest> update(Proyecto proyecto, Long responsableId, String fechaLiberacion, MultipartFile fileF60,
                                                       MultipartFile fileLld, MultipartFile fileHld, MultipartFile fileLayout, MultipartFile fileSla,
                                                       MultipartFile fileReporteFotografico, MultipartFile fileAsignacionFuerzaEspacio,
                                                       MultipartFile fileInventarioHardware, MultipartFile fileAtpFisico, MultipartFile fileAtpFisicoFirmado,
                                                       MultipartFile fileAtpLogico, MultipartFile fileAtpLogicoFirmado, MultipartFile fileReporteTransferenciaOperativa,
                                                       MultipartFile fileCartaResponsivaIaaS, MultipartFile fileCartaResponsivaPlataforma,
                                                       MultipartFile fileCartaResponsivaStorage, MultipartFile fileCartaResponsivaHa, MultipartFile fileCartaResponsivaGsoc) {

        ProyectoResponseRest response = new ProyectoResponseRest();

        response.setMetaList(new ArrayList<>());
        response.setProyectoResponse(new ProyectoResponse());

        try {
            // Recuperamos el proyecto existente o lanzamos excepcion si no existe en la base de datos
            Proyecto proyectoGuardado = proyectoRepository.findById(proyecto.getIdProyecto()).orElseThrow(() ->
                    new EntityNotFoundException("Proyecto con id " + proyecto.getIdProyecto() + " no existe"));

            // Actualizamos los campos basicos (Strings)
            proyectoGuardado.setNombre(proyecto.getNombre());
            proyectoGuardado.setFechaLiberacion(fechaLiberacion);
            proyectoGuardado.setNodos(proyecto.getNodos());

            // Helper local para subir los archivos
            BiFunction<MultipartFile, String, String> guardarODefault = (mpf, defecto) -> {
                if (mpf != null && !mpf.isEmpty()) {
                    try {
                        return uploadFileService.copiarArchivoEnSubCarpeta(
                                proyectoGuardado.getNombre(), mpf);
                    } catch (IOException e) {
                        logger.error("Error al copiar {} en subcarpeta {}: {}",
                                mpf.getOriginalFilename(),
                                proyectoGuardado.getNombre(),
                                e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                } else {
                    // se podra usar el valor antiguamente guardado, si se prefiere:
                    return defecto != null ? defecto : "Pendiente";
                }
            };

            // Actualizamos los archivos
            guardarODefault.apply(fileF60, proyectoGuardado.getF60());
            guardarODefault.apply(fileLld, proyectoGuardado.getLld());
            guardarODefault.apply(fileHld, proyectoGuardado.getHld());
            guardarODefault.apply(fileLayout, proyectoGuardado.getLayout());
            guardarODefault.apply(fileSla, proyectoGuardado.getSla());
            guardarODefault.apply(fileReporteFotografico, proyectoGuardado.getReporteFotografico());
            guardarODefault.apply(fileAsignacionFuerzaEspacio, proyectoGuardado.getAsignacionFuerzaEspacio());
            guardarODefault.apply(fileInventarioHardware, proyectoGuardado.getInventarioHardware());
            guardarODefault.apply(fileAtpFisico, proyectoGuardado.getAtpFisico());
            guardarODefault.apply(fileAtpFisicoFirmado, proyectoGuardado.getAtpFisicoFirmado());
            guardarODefault.apply(fileAtpLogico, proyectoGuardado.getAtpLogico());
            guardarODefault.apply(fileAtpLogicoFirmado, proyectoGuardado.getAtpLogicoFirmado());
            guardarODefault.apply(fileReporteTransferenciaOperativa, proyectoGuardado.getReporteTransferenciaOperativa());
            guardarODefault.apply(fileCartaResponsivaIaaS, proyectoGuardado.getCartaResponsivaIaaS());
            guardarODefault.apply(fileCartaResponsivaPlataforma, proyectoGuardado.getCartaResponsivaPlataforma());
            guardarODefault.apply(fileCartaResponsivaStorage, proyectoGuardado.getCartaResponsivaStorage());
            guardarODefault.apply(fileCartaResponsivaHa, proyectoGuardado.getCartaResponsivaHa());
            guardarODefault.apply(fileCartaResponsivaGsoc, proyectoGuardado.getCartaResponsivaGsoc());

            // Persistir en la base de datos
            proyectoRepository.save(proyectoGuardado);

            // Se arma el response
            response.getMetaList().add(Map.of("code", "00", "message", "¡Se ha actualizado el proyecto exitosamente"));
            response.getProyectoResponse().setProyectos(List.of(proyectoGuardado));
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
    public ResponseEntity<ProyectoResponseRest> delete(Long idProyecto) {

        ProyectoResponseRest response = new ProyectoResponseRest();
        Optional<Proyecto> proyecto = this.proyectoRepository.findById(idProyecto);

        try {
            if (proyecto.isPresent()) {
                // Eliminamos el proyecto por su identificador unico
                this.proyectoRepository.deleteById(idProyecto);
                response.setMetadata("Respuesta exitosa", "00", "¡Proyecto eliminado exitosamente!");
            } else {
                response.setMetadata("Respues fallida", "-1", "¡Proyecto no encontrado!");
                return new ResponseEntity<ProyectoResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.getStackTrace();
            response.setMetadata("Respues fallida", "-1", "¡Error al intentar eliminar el proyecto por su id!");
            return new ResponseEntity<ProyectoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProyectoResponseRest>(response, HttpStatus.OK);
    }
}
