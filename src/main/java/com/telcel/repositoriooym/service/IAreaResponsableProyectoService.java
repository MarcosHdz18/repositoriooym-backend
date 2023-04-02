package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.AreaResponsableProyecto;
import com.telcel.repositoriooym.response.AreaResponsableProyectoResponseRest;
import org.springframework.http.ResponseEntity;

/**
 * @author marcos.hernandez
 */
public interface IAreaResponsableProyectoService {

    /**
     * Metodo que emite una lista de objetos del tipo ResponseEntity
     * @return ResponseEntity de objetos de tipo AreaResponsableProyectoResponseRest
     */
    public ResponseEntity<AreaResponsableProyectoResponseRest> findAll();

    /**
     * Metodo que realiza la busqueda por id
     * @param idAreaResponsableProyecto identificador unico por el que se realizara la busqueda
     * @return ResponseEntity de objetos de tipo AreaResponsableProyectoResponseRest
     */
    public ResponseEntity<AreaResponsableProyectoResponseRest> findById(Long idAreaResponsableProyecto);

    /**
     * Metodo que realiza la persistencia de un objeto en la base de datos
     * @param areaResponsableProyecto objeto que se guardara en la base de datos
     * @return ResponseEntity de objetos de tipo AreaResponsableProyectoResponseRest
     */
    public ResponseEntity<AreaResponsableProyectoResponseRest> save(AreaResponsableProyecto areaResponsableProyecto);

    /**
     * Metodo que realiza la actualizacion de un objeto en la base de datos
     * @param areaResponsableProyecto objeto que se actualizara en la base de datos
     * @param idAreaResponsableProyecto identificador unico para actualizar el objeto
     * @return ResponseEntity de objetos de tipo AreaResponsableProyectoResponseRest
     */
    public ResponseEntity<AreaResponsableProyectoResponseRest> update(AreaResponsableProyecto areaResponsableProyecto, Long idAreaResponsableProyecto);

    /**
     * Metodo que se utilizara para realizar el borrado en la base de datos
     * @param idAreaResponsableProyecto identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity de objetos de tipo AreaResponsableProyectoResponseRest
     */
    public ResponseEntity<AreaResponsableProyectoResponseRest> deleteById(Long idAreaResponsableProyecto);
}
