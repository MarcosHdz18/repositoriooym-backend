package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.Proyecto;
import com.telcel.repositoriooym.response.ProyectoResponseRest;
import org.springframework.http.ResponseEntity;

/**
 * @author marcos.hernandez
 */
public interface IProyectoService {

    /**
     * Metodo que lista todos los proyectos persistentes en la base de datos
     * @return ResponseEntity de ProyectoRest
     */
    public ResponseEntity<ProyectoResponseRest> findAll();

    /**
     * Metodo que lista un objeto de tipo ProyectoResponseRest por su identificador unico
     * @param idProyecto identificador unico del proyecto a buscar
     * @return ResponseEntity de ProyectoResponseRest por su identificador unico
     */
    public ResponseEntity<ProyectoResponseRest> findById(Long idProyecto);

    /**
     * Metodo que realiza la busqueda de un registro por su atributo nombre del proyecto
     * @param nombre String paramentro del nombre del proyecto a buscar
     * @return ResponseEntity del objeto tipo ProductResponseRest
     */
    public ResponseEntity<ProyectoResponseRest> findByNombre(String nombre);

    /**
     * Metodo que realiza la persistencia de un objeto de tipo Proyecto en la base de datos
     * @param proyecto objeto de tipo Proyecto que sera persistido
     * @param responsableId identificador unico para persistir el responsable del proyecto
     * @return ResponseEntity del objeto tipo ProductResponseRest
     */
    public ResponseEntity<ProyectoResponseRest> save(Proyecto proyecto, Long responsableId);

    /**
     * Metodo que permite actualizar un objeto de tipo Proyecto en la base de datos
     * @param proyecto objeto de tipo Proyecto que sera el actualizado mediante su identificador unico
     * @param responsableId objeto de tipo ResponsableProyecto que se actualizara mediante su identificador unico
     * @param idProyecto identificador unico del proyecto del objeto a actualizar
     * @return ResponseEntity del objeto tipo ProductResponseRest
     */
    public ResponseEntity<ProyectoResponseRest> update(Proyecto proyecto, Long responsableId, Long idProyecto);
}
